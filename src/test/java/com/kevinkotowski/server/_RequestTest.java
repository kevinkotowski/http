package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _RequestTest {
    @Test
    public void handleRequestLineAndGet() throws Exception {
        String method = "GET";
        String path = "/test/mock/path";
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);

        request.setMethod(method);
        request.setPath(path);
        assertEquals(HttpMethod.valueOf(method), request.getMethod());
        assertEquals(path, request.getPath());
    }

    @Test
    public void setSocketAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);
        request.setSocket(socket);
        assertEquals( socket, request.getSocket() );
    }
}