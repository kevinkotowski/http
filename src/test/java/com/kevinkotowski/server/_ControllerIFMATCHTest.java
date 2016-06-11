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
        String docRoot = "/public/docRoot";
        IHLogger accessLogger = new MockLogger();

        HttpRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath(path);

        HttpRouter router = new HttpRouter(docRoot, accessLogger);
        IHController controller = new HttpControllerIFMATCH();
        HttpRoute route = new HttpRoute(path, HttpMethod.GET, controller);

        router.registerRoute(route);
        IHResponse response = router.route(request);

        assertEquals("412", response.getResponseCode());
        assertTrue(response.getResponseReason().contains("Failed"));
    }

    @Test
    public void executeIFMATCH() throws Exception {
        // TODO: abstract static file GET to help test this. "getFileEtag"
        // TODO:     should not be a method implemented in IFMATCH

//        String text = "sample text for SHA1 to convert";
//        String correctHash = "5e6a4c69c960170ccb97c325ac62b23d498bb08d";
//
//        HttpRequest request = new HttpRequest(new MockSocket());
//        request.setContent(text);
//        request.addHeader("If-Match: " + correctHash);
//
//        IHController controller = new HttpControllerIFMATCH();
//
//        IHResponse response = controller.execute(request);
//        assertEquals("204", response.getResponseCode());
    }
}