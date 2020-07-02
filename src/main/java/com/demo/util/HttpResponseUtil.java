package com.demo.util;

public class HttpResponseUtil {

    public static String getHttpHeader200(long length) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + length + " \n" +
                "\r\n";
    }

    public static String getHttpHeader404() {
        String content = "<h1>404 Not Found<h1>";
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + content.getBytes().length + " \n" +
                "\r\n" + content;
    }
}
