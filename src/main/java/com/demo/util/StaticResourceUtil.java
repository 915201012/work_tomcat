package com.demo.util;

import java.io.FileInputStream;
import java.io.OutputStream;

public class StaticResourceUtil {

    public static String getAbsolutePath(String path) {
        String url = StaticResourceUtil.class.getResource("/").getPath();
        return url.replaceAll("\\\\", "/") + path;
    }

    public static void outPutStatic(FileInputStream inputStream, OutputStream outputStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        int size = count;
        outputStream.write(HttpResponseUtil.getHttpHeader200(size).getBytes());
        int write = 0;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (write < size) {
            if (write + byteSize > size) {
                byteSize = size - write;
                bytes = new byte[byteSize];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            write += byteSize;
        }

    }
}
