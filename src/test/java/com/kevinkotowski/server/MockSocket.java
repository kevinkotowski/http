package com.kevinkotowski.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockSocket implements IOSocket {
    boolean socketClosed = false;

    public InputStream getInputStream() throws IOException {
        String rawRequest = "GET /mock HTTP/1.1\nHost: test.com\n\n";
        return new ByteArrayInputStream( rawRequest.getBytes(UTF_8) );
    }

    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        };
    }

    public void close() throws IOException {
        this.socketClosed = true;
    }

    public boolean isClosed() {
        return socketClosed;
    }
}
