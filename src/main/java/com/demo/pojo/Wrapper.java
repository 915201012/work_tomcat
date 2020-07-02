package com.demo.pojo;

import com.demo.servlet.HttpServlet;

public class Wrapper {

    private String accessUrl;
    private HttpServlet servlet;

    public Wrapper() {
    }

    public Wrapper(String accessUrl, HttpServlet servlet) {
        this.accessUrl = accessUrl;
        this.servlet = servlet;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                ", accessUrl='" + accessUrl + '\'' +
                ", servlet=" + servlet +
                '}';
    }
}
