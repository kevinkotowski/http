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
        IHLogger logger = new MockLogger();
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("POST");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path, logger);
        IHController controller = new HttpControllerUPSERT();
        HttpRoute route = new HttpRoute(path, HttpMethod.PUT, controller);

        router.registerRoute(route);
        IHResponse response = router.route(request);

        assertEquals("405", response.getResponseCode());
    }
}