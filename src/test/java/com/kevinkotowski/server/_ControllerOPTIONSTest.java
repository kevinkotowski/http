package com.kevinkotowski.server;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/8/16.
 */
public class _ControllerOPTIONSTest {
    @Test
    public void executeOPTIONS() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/docroot/file";
        IHLogger logger = new MockLogger();
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("OPTIONS");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path, logger);
        IHController controller = new HttpControllerUPDATE();
        HttpRoute route = new HttpRoute(path, HttpMethod.POST, controller);

        router.registerRoute(route);
        IHResponse response = router.route(request);
        List<String> headers = response.getHeaders();

        assertEquals("200", response.getResponseCode());
        assertEquals("Allow: OPTIONS,POST", headers.get(0));
    }
}