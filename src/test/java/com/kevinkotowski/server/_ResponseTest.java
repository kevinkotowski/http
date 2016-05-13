package com.kevinkotowski.server;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _ResponseTest {
    @Test
    public void requestToResponseLifecycle() throws Exception {
        HttpRequest request = new HttpRequest();
        HttpResponse response = new HttpResponse();
        IOSocket socket = new MockSocket();

        request.handleRequestLine("GET /mock/file/is/fake.html HTTP/1.1");
        response.setSocket(socket);

        response.setResponseCode( request.getResponseCode() );
        response.setResponseReason( request.getResponseReason() );
        response.setBody("<html><p>Fake file content</p></html>");
        assertTrue( response.getBody().contains("Fake") );
    }
}