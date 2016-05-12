package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class MockNetwork implements IONetwork {
    private int port = -1;

    public MockNetwork(int port) throws IOException {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public IORequest next() {
        return new MockRequest("GET", "/mock/test/path.html");
    }
}
