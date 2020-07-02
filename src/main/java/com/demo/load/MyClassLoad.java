package com.demo.load;

import java.io.*;


public class MyClassLoad extends ClassLoader {

    private String location = "D:/Users/webapps/one/com/demo/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoad classLoad = new MyClassLoad();
        Class<?> aClass = classLoad.findClass("D:/Users/webapps/one/com/demo/OneServlet.class");
        System.out.println(aClass);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassDate(name);
        Class<?> aClass = defineClass(bytes, 0, bytes.length);
        return aClass;
    }

    private byte[] loadClassDate(String name) {
        FileInputStream fis = null;
        byte[] datas = null;
        try {
            fis = new FileInputStream(name);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                bos.write(b);
            }
            datas = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return datas;
    }
}

