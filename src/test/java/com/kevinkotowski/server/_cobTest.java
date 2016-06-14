package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _CobTest {
    @Test
    public void createServerAndListen() throws Exception {
        IHServer server = new MockServer();
        Cob cob = new Cob(server);
        assertTrue( server.status().contains("listening") );
    }

    @Test
    public void getDefaultRouter() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Cob.getRouter(docRoot, logger);

        assertEquals(docRoot, router.getDocRoot());
        String options = router.getOptions("/");
        assertEquals("OPTIONS,GET,HEAD", options);
    }

    @Test
    public void routerConfigured() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Cob.getRouter(docRoot, logger);

        IHMiddleware middleware = Cob.getMiddleware();

        // Cob custom configures route to controllerTEACUP
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
        IHRouter router = Cob.getRouter(docRoot, logger);

        IHMiddleware middleware = Cob.getMiddleware();

        // the default controller if no method is controllerINVALID
        IHRequest request = new HttpRequest(new MockSocket());
        IHResponse response = middleware.transform(request, router);
        assertEquals("405", response.getResponseCode());

    }

    @Test
    public void customMiddleware() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Cob.getRouter(docRoot, logger);

        IHMiddleware middleware = Cob.getMiddleware();

        // Cob Custom middleware is controllerREDIRECT
        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath("/redirect");
        request.setMethod("GET");
        IHResponse response = middleware.transform(request, router);
        assertEquals("302", response.getResponseCode());
    }
}