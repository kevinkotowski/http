package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class _ServerTest {
    @Test
    public void listenToServer() throws Exception {
        IHNetwork network = new MockNetwork();
        IHRouter router = new MockRouter("/mockRoot");

        IHServer server = new HttpServer(network, new HttpMiddleware(), router);
        server.listen();
        assertTrue( server.status().contains("Listening") );
    }

    @Test
    public void closeServer() throws Exception {
        IHNetwork network = new MockNetwork();
        IHRouter router = new MockRouter("/mockRoot");

        IHServer server = new HttpServer(network, new HttpMiddleware(), router);
        server.listen();
        server.close();
        assertTrue( server.status().contains("Stopped") );
    }
}