package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class MockSockets implements IOSockets {
    private int port = -1;

    public MockSockets(int port) throws IOException {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public IORequest next() {
        return null;
    }
}
