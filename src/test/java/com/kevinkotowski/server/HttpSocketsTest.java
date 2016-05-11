package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/11/16.
 */
public class HttpSocketsTest {
    @Test
    public void startListeningToPort() throws Exception {
        int port = 3210;
        IOSockets mockSockets = new MockSockets(port);
        assertEquals( port, mockSockets.getPort() );
    }

    @Test
    public void getNextIORequestFromPort() throws Exception {
        int port = 3210;
        IOSockets mockSockets = new MockSockets(port);
        IORequest request = mockSockets.next();
    }

}