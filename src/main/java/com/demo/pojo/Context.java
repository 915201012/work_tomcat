package com.demo.pojo;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private String hostName;
    private String demoName;
    List<Wrapper> wrapperList = new ArrayList<>();

    public Context() {
    }

    public Context(String hostName, String demoName, List<Wrapper> wrapperList) {
        this.hostName = hostName;
        this.demoName = demoName;
        this.wrapperList = wrapperList;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<Wrapper> wrapperList) {
        this.wrapperList = wrapperList;
    }
}
