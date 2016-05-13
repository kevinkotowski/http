package com.kevinkotowski.server;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _SocketTest {
    @Test
    public void getInputStream() throws Exception {
        IOSocket socket = new MockSocket();
        InputStream inputStream = socket.getInputStream();
        assertNotNull(inputStream);
    }

    @Test
    public void getOutputStreamAndClose() throws Exception {
        IOSocket socket = new MockSocket();
        OutputStream outputStream = socket.getOutputStream();
        socket.close();
        assertNotNull(outputStream);
    }
}