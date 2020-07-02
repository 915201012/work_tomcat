package com.demo.servlet;

import com.demo.pojo.Request;
import com.demo.pojo.Response;

public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);

    @Override
    public void service(Request request, Response response) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
