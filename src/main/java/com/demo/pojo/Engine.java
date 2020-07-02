package com.demo.pojo;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    List<Host> hostList = new ArrayList<>();

    public Engine() {
    }

    public Engine(List<Host> hostList) {
        this.hostList = hostList;
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }
}
