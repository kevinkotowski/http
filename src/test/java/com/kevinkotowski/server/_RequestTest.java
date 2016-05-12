package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _RequestTest {
    @Test
    public void handleRequestLineAndGet() throws Exception {
        String requestLine = "GET /test/mock/path HTTP/1.1";
        HttpRequest request = new HttpRequest();
        request.handleRequestLine(requestLine);
        assertEquals("GET", request.getMethod());
        assertEquals("./test/mock/path", request.getPath());
    }

    @Test
    public void setSocketAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest();
        request.setSocket(socket);
        assertEquals( socket, request.getSocket() );
    }

    @Test
    public void getResponseStatusDataFromRequestLine() throws Exception {
        String requestLine = "GET /test/mock/path HTTP/1.1";
        HttpRequest request = new HttpRequest();
        request.handleRequestLine(requestLine);
        assertEquals("200", request.getResponseCode());
        assertTrue( request.getResponseReason().contains("OK") );
    }
}