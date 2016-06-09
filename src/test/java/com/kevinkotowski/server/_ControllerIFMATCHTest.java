package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/8/16.
 */
public class _ControllerIFMATCHTest {
    @Test
    public void executeIFMATCHnoMatch() throws Exception {
        IOSocket socket = new MockSocket();
        String path = "/file_to_edit.txt";
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath(path);

        HttpRouter router = new HttpRouter(path);
        IHController controller = new HttpControllerIFMATCH();
        HttpRoute route = new HttpRoute(path, HttpMethod.GET, controller);

        router.registerRoute(route);
        IHResponse response = router.route(request);

        assertEquals("412", response.getResponseCode());
        assertTrue(response.getResponseReason().contains("Failed"));
    }

    @Test
    public void executeIFMATCH() throws Exception {
       // TODO: needs mock File/Directory
    }

    @Test
    public void makeSHA1Hash() throws Exception {
        HttpControllerIFMATCH controller = new HttpControllerIFMATCH();
        String sha = controller.makeSHA1Hash("boo");
        assertEquals("78b371f0ea1410abc62ccb9b7f40c34288a72e1a", sha);
    }
}