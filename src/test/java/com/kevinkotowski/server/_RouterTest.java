package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/29/16.
 */
public class _RouterTest {
    @Test
    public void routerLifecycle() throws Exception {
        String docRoot = "/home/mock";
        String path = "/mock/path";

        IHController controllerGET = new MockController();
        IHController controllerPOST = new MockController();
        IHController controllerBAD = new MockController();
        IHRoute routeGET = new HttpRoute(path, HttpMethod.GET,
                controllerGET);
        IHRoute routePOST = new HttpRoute(path, HttpMethod.POST,
                controllerPOST);

        IHRouter router = new HttpRouter(docRoot);
        router.registerRoute(routeGET);
        router.registerRoute(routePOST);

        IOSocket socket = new MockSocket();
        IORequest requestGET = new MockRequest(socket);
        requestGET.handleRequestLine("GET /mock/path HTTP/1.1");

        IORequest requestPOST = new MockRequest(socket);
        requestPOST.handleRequestLine("POST /mock/path HTTP/1.1");

        IORequest requestBAD = new MockRequest(socket);
        requestBAD.handleRequestLine("DELETE /mock/path HTTP/1.1");

        assertEquals(controllerGET, router.route(requestGET) );
        assertEquals(controllerPOST, router.route(requestPOST) );
        assertNotEquals(controllerBAD, router.route(requestBAD) );
    }

    @Test
    public void getOptions() throws Exception {
        String docRoot = "/home/mock";
        String path = "/mock/path";

        IHRouter router = new HttpRouter(docRoot);

        IHController controller = new MockController();
        IHRoute routeGET = new HttpRoute(path, HttpMethod.GET,
                controller);
        router.registerRoute(routeGET);

        assertEquals("GET", router.getOptions(path));

        IHRoute routePOST = new HttpRoute(path, HttpMethod.POST,
                controller);
        router.registerRoute(routePOST);

        assertEquals("GET, POST", router.getOptions(path));
    }

}