package com.demo.servlet;

import com.demo.pojo.Request;
import com.demo.pojo.Response;

public interface Servlet {

    void init();

    void destory();

    void service(Request request, Response response);

}
