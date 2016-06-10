package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class MockNetwork implements IHNetwork {
    public IHRequest next() throws IOException {
        MockSocket socket = new MockSocket();
        IHRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath("/mock/next/file.html");
        return request;
    }
}
