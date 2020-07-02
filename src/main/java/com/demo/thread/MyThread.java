package com.demo.thread;

import com.demo.pojo.Request;
import com.demo.pojo.Response;
import com.demo.servlet.HttpServlet;

import java.net.Socket;
import java.util.Map;

public class MyThread extends Thread {


    private Socket socket;
    private Map<String, HttpServlet> servletMap;

    public MyThread() {

    }

    public MyThread(Socket socket, Map<String, HttpServlet> map) {
        this.socket = socket;
        this.servletMap = map;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            if (servletMap.containsKey(request.getUrl())) {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            } else {
                response.outPutHtml(request.getUrl());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
