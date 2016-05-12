package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockSocket implements IOSocket {
    public InputStream getInputStream() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }

    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        };
    }

    public void close() throws IOException { }
}
