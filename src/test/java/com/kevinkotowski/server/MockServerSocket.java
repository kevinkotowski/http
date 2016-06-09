package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class MockServerSocket implements IOServerSocket {
    public IOSocket accept() throws IOException {
        return new MockSocket();
    }
}
