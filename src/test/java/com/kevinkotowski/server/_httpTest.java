package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class _httpTest {
    @Test
    public void createAndListenToServer() throws Exception {
        Server server = new MockServer();
        http http = new http(server);
        assertTrue( server.status().contains("listening") );
    }
}