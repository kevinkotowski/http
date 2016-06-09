package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/11/16.
 */
public class _NetworkTest {
    @Test
    public void getNextRequest() throws Exception {
        IOServerSocket serverSocket = new MockServerSocket();
        IHNetwork network = new HttpNetwork(serverSocket,
                new MockRequestParser());
        IHRequest request = network.next();
        assertEquals( HttpMethod.HEAD, request.getMethod() );
    }
}