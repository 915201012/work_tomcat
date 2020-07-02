package com.demo.pojo;

import java.io.InputStream;

public class Request {

    private String method;
    private String url;
    private InputStream inputStream;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request() {
    }

    public Request(InputStream inputStream) throws Exception {
        this.inputStream = inputStream;
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String str = new String(bytes);
        String firstLine = str.split("\\n")[0];
        String[] split = firstLine.split(" ");
        this.method = split[0];
        this.url = split[1];
        System.out.println("Request  method : " + method);
        System.out.println("Request  url : " + url);
    }
}
