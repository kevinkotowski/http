package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class _httpTest {
    @Test
    public void createServerAndListen() throws Exception {
        IHServer server = new MockServer();
        http http = new http(server);
        assertTrue( server.status().contains("listening") );
    }

    @Test
    public void getDefaultRouter() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = http.getRouter(docRoot, logger);

        assertEquals(docRoot, router.getDocRoot());
        String options = router.getOptions("/");
        assertEquals("OPTIONS,GET,HEAD", options);
    }
}