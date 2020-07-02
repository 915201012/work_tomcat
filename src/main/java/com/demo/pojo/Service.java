package com.demo.pojo;

public class Service {
    private int port;
    private Engine engine;

    public Service() {
    }

    public Service(int port, Engine engine) {
        this.port = port;
        this.engine = engine;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
