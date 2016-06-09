package com.kevinkotowski.server;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _ResponseTest {
    @Test(expected = IllegalStateException.class)
    public void runResponse() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        response.run();
        // on second execution, socket is closed so exception
        response.run();
    }

    @Test(expected = IllegalStateException.class)
    public void setNewWithSocketAndCloseIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        response.closeSocket();
        // socket is closed, so exception is thrown
        response.run();
    }

    @Test(expected = RuntimeException.class)
    public void setResponseAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        response.setResponseCode("418");
        assertEquals("418", response.getResponseCode());

        response.setResponseReason("Kevin is cool");
        assertEquals("Kevin is cool", response.getResponseReason());

        // this response code is too long and will throw an error
        response.setResponseCode("1234");
    }

    @Test
    public void getStatusLine() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        assertTrue(response.getStatusLine().contains("HTTP/1.1"));
    }

    @Test
    public void setHeaderAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        String header = "Fake Header: true";
        response.addHeader(header);

        List<String> headers = response.getHeaders();
        assertEquals(header, headers.get(0));
    }

    @Test
    public void setBodyAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        String body = "corpus";
        response.setBody(body);

        assertEquals(body, response.getBody());
    }

    @Test
    public void setImageAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpResponse response = new HttpResponse(socket);

        byte[] image = "someimagebytessimulated".getBytes();
        response.setImage(image);

        // verify no exception thrown
        response.writeImage();
    }
}