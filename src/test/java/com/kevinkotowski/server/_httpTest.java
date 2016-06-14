package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class _HttpTest {
    @Test
    public void createServerAndListen() throws Exception {
        IHServer server = new MockServer();
        Http http = new Http(server);
        assertTrue( server.status().contains("listening") );
    }

    @Test
    public void getDefaultRouter() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Http.getRouter(docRoot, logger);

        assertEquals(docRoot, router.getDocRoot());
        String options = router.getOptions("/");
        assertEquals("OPTIONS,GET,HEAD", options);
    }

    @Test
    public void getDefaultMiddlewareINVALID() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Http.getRouter(docRoot, logger);

        IHMiddleware middleware = Http.getMiddleware();

        // the default controller if no method is controllerINVALID
        IHRequest request = new HttpRequest(new MockSocket());
        IHResponse response = middleware.transform(request, router);
        assertEquals("405", response.getResponseCode());

    }

    @Test
    public void getDefaultMiddlewareSTATIC() throws Exception {
        String docRoot = "httpRoot";
        IHLogger logger = new MockLogger();
        IHRouter router = Http.getRouter(docRoot, logger);

        IHMiddleware middleware = Http.getMiddleware();

        // the default controller if no method is controllerSTATIC
        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath("/");
        request.setMethod("GET");
        IHResponse response = middleware.transform(request, router);
        assertEquals("404", response.getResponseCode());
    }
}