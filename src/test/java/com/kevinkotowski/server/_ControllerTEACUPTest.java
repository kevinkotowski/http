package com.kevinkotowski.server;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/8/16.
 */
public class _ControllerTEACUPTest {
    @Test
    public void executeTEACUPokay() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/tea";
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path);
        IHController controller = new HttpControllerTEACUP();
        HttpRoute route = new HttpRoute(path, HttpMethod.GET, controller);

        router.registerRoute(route);
        IOResponse response = router.route(request);

        assertEquals("200", response.getResponseCode());
        assertTrue(response.getResponseReason().contains("Tip"));
    }

    @Test
    public void verify418() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/coffee";
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path);
        IHController controller = new HttpControllerTEACUP();
        HttpRoute route = new HttpRoute(path, HttpMethod.GET, controller);

        router.registerRoute(route);
        IOResponse response = router.route(request);

        assertEquals("418", response.getResponseCode());
        assertTrue(response.getResponseReason().contains("teapot"));
    }
}