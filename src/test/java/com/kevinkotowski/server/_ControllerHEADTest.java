package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class _ControllerHEADTest {
    @Test
    public void executeHEAD() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/";
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("HEAD");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path);
        IHController controller = new HttpControllerHEAD();
        HttpRoute route = new HttpRoute(path, HttpMethod.HEAD, controller);

        router.registerRoute(route);
        IOResponse response = router.route(request);
        assertEquals("200", response.getResponseCode());
    }
}