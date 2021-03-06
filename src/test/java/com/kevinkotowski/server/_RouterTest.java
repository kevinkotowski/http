package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/29/16.
 */
public class _RouterTest {
    @Test
    public void routerInit() throws Exception {
        String docRoot = "/home/mock";
        IHLogger logger = new MockLogger();
        IHRouter router = new HttpRouter(docRoot, logger);
        assertEquals(router.getDocRoot(), docRoot);
    }

    @Test
    public void routerRouteRegistration() throws Exception {
        // this test needs multiple routes to have any meaning
        String docRoot = "/home/mock";
        IHLogger logger = new MockLogger();
        String path = "/mock/path";

        IHController controllerGET = new MockController();
        IHController controllerPOST = new MockController();
        IHRoute routeGET = new HttpRoute(path, HttpMethod.GET,
                controllerGET);
        IHRoute routePOST = new HttpRoute(path, HttpMethod.POST,
                controllerPOST);

        IHRouter router = new HttpRouter(docRoot, logger);
        router.registerRoute(routeGET);
        router.registerRoute(routePOST);

        IOSocket socket = new MockSocket();
        IHRequest requestGET = new HttpRequest(socket);
        requestGET.setMethod("GET");
        requestGET.setPath("/mock/path/file.html");

        IHRequest requestPOST = new HttpRequest(socket);
        requestPOST.setMethod("GET");
        requestPOST.setPath("/mock/path/file.html");

        IHRequest requestBAD = new HttpRequest(socket);
        requestBAD.setMethod("PUT");
        requestBAD.setPath("/mock/path/file.html");

        assertNotNull( router.route(requestGET) instanceof IHController);
        assertNotNull( router.route(requestPOST) instanceof IHController);
        assertEquals( "405", router.route(requestBAD).getResponseCode() );
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateRoute() throws Exception {
        String docRoot = "/home/mock";
        IHLogger logger = new MockLogger();
        String path = "/mock/path";

        IHController controllerGET = new MockController();
        IHRouter router = new HttpRouter(docRoot, logger);
        IHRoute routeGET = new HttpRoute(path, HttpMethod.GET,
                controllerGET);
        router.registerRoute(routeGET);
        // registering the same route 2x not valid state, so this throws error
        router.registerRoute(routeGET);
    }

    @Test
    public void getOptions() throws Exception {
        String docRoot = "/home/mock";
        IHLogger logger = new MockLogger();
        String path = "/mock/path";

        IHRouter router = new HttpRouter(docRoot, logger);

        IHController controller = new MockController();
        IHRoute routeGET = new HttpRoute(path, HttpMethod.GET,
                controller);
        router.registerRoute(routeGET);

        assertEquals("OPTIONS,GET,HEAD", router.getOptions(path));

        IHRoute routePOST = new HttpRoute(path, HttpMethod.POST,
                controller);
        router.registerRoute(routePOST);

        assertEquals("OPTIONS,GET,HEAD,POST", router.getOptions(path));
    }
}