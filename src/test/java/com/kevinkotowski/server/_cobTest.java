package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _cobTest {
    @Test
    public void createServerAndListen() throws Exception {
        IHServer server = new MockServer();
        cob cob = new cob(server);
        assertTrue( server.status().contains("listening") );
    }

    @Test
    public void getOverriddenRouter() throws Exception {
        String docRoot = "cobRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = cob.getRouter(docRoot, logger);

        assertEquals(docRoot, router.getDocRoot());

        String dynamicOptions = router.getOptions("/redirect");
        assertEquals("OPTIONS,GET,HEAD", dynamicOptions);

        String staticOptions = router.getOptions("/file1");
        assertEquals("OPTIONS,GET,HEAD", staticOptions);
    }
}