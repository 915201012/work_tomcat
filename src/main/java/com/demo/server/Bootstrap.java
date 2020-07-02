package com.demo.server;

import com.demo.load.MyClassLoad;
import com.demo.pojo.*;
import com.demo.servlet.HttpServlet;
import com.demo.thread.CustomThread;
import com.demo.thread.MyThread;
import com.demo.util.HttpResponseUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Bootstrap {

    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MiniCat start on port : " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            String content = "MiniCat Version 1.0 ";
            String response = HttpResponseUtil.getHttpHeader200(content.getBytes().length) + content;
            outputStream.write(response.getBytes());
            socket.close();
        }
    }

    private void start2() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MiniCat start on port : " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            response.outPutHtml(request.getUrl());
            socket.close();
        }
    }


    private ThreadPoolExecutor threadPoolExecutor;

    private void initThreadPool() {
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100l;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);
    }

    private void start3() throws Exception {
        initThreadPool();
        loadServlet();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MiniCat start on port : " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            MyThread myThread = new MyThread(socket, servletMap);
            threadPoolExecutor.execute(myThread);
        }
    }

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    private void loadServlet() throws Exception {
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
//        SAXReader saxReader = new SAXReader();
//        Document document = saxReader.read(inputStream);
//        List<Element> nodes = document.selectNodes("//servlet");
//        for (int i = 0; i < nodes.size(); i++) {
//            Element element = nodes.get(i);
//            Element snElement = (Element) element.selectSingleNode("servlet-name");
//            String servletName = snElement.getStringValue();
//            Element scElement = (Element) element.selectSingleNode("servlet-class");
//            String servletClass = scElement.getStringValue();
//            Element mappingElement = (Element) document.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
//            Element patternElement = (Element) mappingElement.selectSingleNode("url-pattern");
//            String pattern = patternElement.getStringValue();
//            servletMap.put(pattern, (HttpServlet) Class.forName(servletClass).newInstance());
//        }
    }


    private Service service;
    private Map<String, String> stringMap = new HashMap<>();
    private MyClassLoad load = new MyClassLoad();


    private void start4() throws Exception {
        loadServerConfig();
        loadWebConfig();
        System.out.println("init success");
        ServerSocket serverSocket = new ServerSocket(service.getPort());
        System.out.println("MiniCat 4.0 start on port : " + service.getPort());
        initThreadPool();
        while (true) {
            Socket socket = serverSocket.accept();
            CustomThread myThread = new CustomThread(socket, service);
            threadPoolExecutor.execute(myThread);
        }


    }

    private void loadWebConfig() throws Exception {
        List<Host> hostList = service.getEngine().getHostList();
        if (hostList.size() == 0) return;
        for (Host host : hostList) {
            loadServlet(host);
        }
        initContext();

    }

    public void initContext() throws Exception {
        if (stringMap.isEmpty()) return;
        for (Host host : service.getEngine().getHostList()) {
            String hostName = host.getName();
            String appBase = host.getAppBase();
            List<Context> contexts = new ArrayList<>();
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                String demoName = entry.getKey();
                String value = entry.getValue();
                Context context = new Context();
                context.setHostName(hostName);
                context.setDemoName(demoName);
                List<Wrapper> wrappers = new ArrayList<>();
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(new FileInputStream(entry.getValue()));
                List<Element> nodes = document.selectNodes("//servlet");
                for (int i = 0; i < nodes.size(); i++) {
                    Wrapper wrapper = new Wrapper();
                    Element element = nodes.get(i);
                    Element sne = (Element) element.selectSingleNode("servlet-name");
                    String servletName = sne.getStringValue();
                    Element sce = (Element) element.selectSingleNode("servlet-class");
                    String classPath = sce.getStringValue();
                    classPath = classPath.replace(".", "/");
                    Element me = (Element) document.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                    Element pe = (Element) me.selectSingleNode("url-pattern");
                    String pattern = pe.getStringValue();
                    String url = "/" + demoName + pattern;
                    wrapper.setAccessUrl(url);
                    classPath = appBase + "/" + demoName + "/" + classPath + ".class";
                    Class<?> aClass = load.findClass(classPath);
                    Object o = aClass.newInstance();
                    wrapper.setServlet((HttpServlet) o);
                    wrappers.add(wrapper);
                }
                context.setWrapperList(wrappers);
                contexts.add(context);
            }
            host.setContextList(contexts);
        }

    }

    private void loadServlet(Host host) {
        String name = host.getName();
        String appBase = host.getAppBase();
        parseAppBase(appBase);
    }


    private void parseAppBase(String appBase) {
        File abf = new File(appBase);
        if (!(abf.exists() && abf.isDirectory())) return;
        File[] files = abf.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getName();
                File[] listFiles = file.listFiles();
                InputStream inputStream = null;
                for (File f : listFiles) {
                    if (f.isFile() && f.getName().endsWith(".xml")) {
                        String absolutePath = f.getAbsolutePath();
                        stringMap.put(name, absolutePath);
                    }
                }
            }
        }
    }

    private void loadServerConfig() throws Exception {
        service = new Service();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element se = (Element) document.selectSingleNode("//Service");
        Element ce = (Element) se.selectSingleNode("Connector");
        String port = ce.attributeValue("port");
        service.setPort(Integer.parseInt(port));
        Element ee = (Element) se.selectSingleNode("Engine");
        List<Element> elements = ee.selectNodes("Host");
        Engine engine = new Engine();
        List<Host> hosts = new ArrayList<>();
        for (Element e : elements) {
            String name = e.attributeValue("name");
            String appBase = e.attributeValue("appBase");
            Host host = new Host();
            host.setName(name);
            host.setAppBase(appBase);
            hosts.add(host);
        }
        engine.setHostList(hosts);
        service.setEngine(engine);

    }


    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
