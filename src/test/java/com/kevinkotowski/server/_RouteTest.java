package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/29/16.
 */
public class _RouteTest {
    @Test
    public void routeInitGetSet() throws Exception {
        String path = "/mock/path";
        IHController controller = new MockController();
        IHRoute route = new HttpRoute(path, HttpMethod.GET,
                controller);
        assertEquals( HttpMethod.GET, route.getMethod() );
        assertEquals( path, route.getPath() );
    }

    @Test
    public void resolveController() throws Exception {
        String path = "/mock/path";
        IHController controller = new MockController();
        IHRoute route = new HttpRoute(path, HttpMethod.GET,
                controller);
        assertEquals( controller, route.getController(path, HttpMethod.GET) );
    }
}