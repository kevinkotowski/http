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
    public void getDefaultRouter() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = cob.getRouter(docRoot, logger);

        assertEquals(docRoot, router.getDocRoot());
        String options = router.getOptions("/");
        assertEquals("OPTIONS,GET,HEAD", options);
    }

    @Test
    public void routerConfigured() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = cob.getRouter(docRoot, logger);

        IHMiddleware middleware = cob.getMiddleware();

        // cob custom configures route to controllerTEACUP
        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath("/coffee");
        request.setMethod("GET");
        IHResponse response = middleware.transform(request, router);
        assertEquals("418", response.getResponseCode());
    }

    @Test
    public void getDefaultMiddleware() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = cob.getRouter(docRoot, logger);

        IHMiddleware middleware = cob.getMiddleware();

        // the default controller if no method is controllerINVALID
        IHRequest request = new HttpRequest(new MockSocket());
        IHResponse response = middleware.transform(request, router);
        assertEquals("405", response.getResponseCode());

    }

    @Test
    public void customMiddleware() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = cob.getRouter(docRoot, logger);

        IHMiddleware middleware = cob.getMiddleware();

        // cob Custom middleware is controllerREDIRECT
        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath("/redirect");
        request.setMethod("GET");
        IHResponse response = middleware.transform(request, router);
        assertEquals("302", response.getResponseCode());
    }
}