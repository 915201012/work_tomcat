package com.demo.pojo;

import com.demo.util.HttpResponseUtil;
import com.demo.util.StaticResourceUtil;

import java.io.*;

public class Response {
    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    public void outPutHtml(String path) throws Exception {
        String absolutePath = StaticResourceUtil.getAbsolutePath(path);
        File file = new File(absolutePath);
        if (file.exists() && file.isFile()) {
            StaticResourceUtil.outPutStatic(new FileInputStream(file), outputStream);
        } else {
            outPut(HttpResponseUtil.getHttpHeader404());
        }
    }

    public void outPut(String content) {
        try {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
