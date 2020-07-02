package com.demo.pojo;

import java.util.ArrayList;
import java.util.List;

public class Host {
    private String name;
    private String appBase;
    private List<Context> contextList = new ArrayList<>();

    public Host() {
    }

    public Host(String name, String appBase, List<Context> contextList) {
        this.name = name;
        this.appBase = appBase;
        this.contextList = contextList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }

    public List<Context> getContextList() {
        return contextList;
    }

    public void setContextList(List<Context> contextList) {
        this.contextList = contextList;
    }
}
