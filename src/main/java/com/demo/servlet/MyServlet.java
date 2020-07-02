package com.demo.servlet;

import com.demo.pojo.Request;
import com.demo.pojo.Response;
import com.demo.util.HttpResponseUtil;

public class MyServlet extends HttpServlet {

    @Override
    public void doGet(Request request, Response response) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String content = "<h1>MyServlet doGet</h1>";
        response.outPut(HttpResponseUtil.getHttpHeader200(content.getBytes().length) + content);
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>MyServlet doPost</h1>";
        response.outPut(HttpResponseUtil.getHttpHeader200(content.getBytes().length) + content);
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
