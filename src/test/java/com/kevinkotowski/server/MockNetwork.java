package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class MockNetwork implements IHNetwork {
    private int port = -1;

    public MockNetwork(int port) throws IOException {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public IHRequest next() throws IOException {
        MockSocket socket = new MockSocket();
        IHRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath("/mock/next/file.html");
        return request;
    }
}
