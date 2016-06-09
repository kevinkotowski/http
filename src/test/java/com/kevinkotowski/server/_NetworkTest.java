package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/11/16.
 */
public class _NetworkTest {
    @Test
    public void startListeningToPort() throws Exception {
        int port = 3210;
        IHNetwork mockNetwork = new MockNetwork(port);
        assertEquals( port, mockNetwork.getPort() );
    }

    @Test
    public void getNextRequestFromPort() throws Exception {
        int port = 3210;
        IHNetwork mockNetwork = new MockNetwork(port);
        IHRequest request = mockNetwork.next();
        assertEquals( HttpMethod.GET, request.getMethod() );
    }
}