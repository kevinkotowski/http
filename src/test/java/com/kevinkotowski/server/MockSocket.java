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
    ByteArrayInputStream response =
           new ByteArrayInputStream( "GET /mock HTTP/1.1\n\n".getBytes(UTF_8) );

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
