package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockServer implements Server {
    String status;

    public MockServer() {
        this.status = "MockServer is waiting.";
    }
    public void listen() {
        this.status = "MockServer is listening.";
    }

    public void close() {
        this.status = "MockServer is closed.";
    }

    public String status() {
        return this.status;
    }
}