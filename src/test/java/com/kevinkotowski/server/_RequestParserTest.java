package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _RequestParserTest {
    @Test
    public void parseRequest() throws Exception {
        IHRequestParser parser = new HttpRequestParser();
        IOSocket socket = new MockSocket();

        IHRequest request = parser.parse(socket);
        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/mock", request.getPath());
    }
}