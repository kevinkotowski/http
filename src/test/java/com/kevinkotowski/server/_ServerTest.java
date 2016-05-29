package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class _ServerTest {

    @Test
    public void createAndListenToServer() throws Exception {
        IHServer server = new MockServer();
        server.listen();
        assertTrue( server.status().contains("listening") );
    }

    @Test
    public void createAndCloseServer() throws Exception {
        IHServer server = new MockServer();
        server.close();
        assertTrue( server.status().contains("closed") );
    }
}