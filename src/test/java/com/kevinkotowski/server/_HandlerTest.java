package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public class _HandlerTest {
    @Test
    public void handle() throws Exception {
        String path = "/test/path";
        IHMiddleware middleware = new HttpMiddleware();
        IHRouter router = new HttpRouter("/testRoot",
                new HttpLogger(new MockFile("log")));
        router.registerRoute(new HttpRoute("path", HttpMethod.GET,
                new MockController()));

        IHHandler handler = new HttpHandler(middleware, router);
        IHRequest request = new HttpRequest(new MockSocket());
        IHResponse response = handler.handle(request);
        assertTrue( response.getStatusLine().contains("HTTP/1.1") );
    }
}