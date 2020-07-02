package com.demo.thread;

import com.demo.pojo.*;

import java.net.Socket;
import java.util.List;

public class CustomThread extends Thread {

    private Socket socket;
    private Service service;

    public CustomThread(Socket socket, Service service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            List<Host> hostList = service.getEngine().getHostList();
            String url = request.getUrl();  //  /one/my
            String name = url.split("/")[1];
            String servletUrl = url.split("/")[2];
            System.out.println("hostName: " + name + " ,servletUrl :" + servletUrl);
            Host host = hostList.get(0);
            List<Context> contextList = host.getContextList();
            for (Context context : contextList) {
                if (context.getDemoName().equals(name)) {
                    List<Wrapper> wrapperList = context.getWrapperList();
                    for (Wrapper wrapper : wrapperList) {
                        if (wrapper.getAccessUrl().equals(url)) {
                            wrapper.getServlet().service(request, response);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
