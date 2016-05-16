package com.kevinkotowski.server;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _HandlerTest {
    @Test
    public void interfaceHandleRequestReturnResponse() throws Exception {
        Handler handler = new MockHandler();
        HttpRequest request = new HttpRequest();
        IOResponse response = handler.handle(request);
        Assert.assertNotNull(response);
    }

    @Test
    public void handleErrorGetNotFound404() throws Exception {
        Handler handler = new HttpHandler("/mock/file/path");
        IORequest request = new MockRequest();
        request.handleRequestLine("GET file/not/found.html HTTP/1.1");
        IOResponse response = handler.handle(request);
        Assert.assertEquals("404", response.getResponseCode());
    }

    @Test
    public void handleErrorBadMethod405() throws Exception {
        Handler handler = new HttpHandler("/mock/file/path");
        IORequest request = new MockRequest();
        request.handleRequestLine("BOO file/not/found.html HTTP/1.1");
        IOResponse response = handler.handle(request);
        Assert.assertEquals("405", response.getResponseCode());
    }
}