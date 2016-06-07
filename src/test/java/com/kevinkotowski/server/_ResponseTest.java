package com.kevinkotowski.server;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _ResponseTest {
    @Test
    public void responseLifecycle() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        //TODO: This is an inadequate test

        response.setBody("<html><p>Fake file content</p></html>");
        assertTrue( response.getBody().contains("Fake") );
    }
}