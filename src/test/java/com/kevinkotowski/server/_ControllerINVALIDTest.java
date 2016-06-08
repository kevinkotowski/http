package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/8/16.
 */
public class _ControllerINVALIDTest {
    @Test
    public void executeINVALID() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/docroot/file";
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("POST");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path);
        IHController controller = new HttpControllerUPDATE();
        HttpRoute route = new HttpRoute(path, HttpMethod.PUT, controller);

        router.registerRoute(route);
        IOResponse response = router.route(request);

        assertEquals("405", response.getResponseCode());
    }
}