package com.kevinkotowski.server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class _RequestTest {
    @Test
    public void setNewSocketAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);

        IOSocket newSocket = new MockSocket();
        request.setSocket(newSocket);

        assertNotEquals( socket, newSocket );
        assertEquals( newSocket, request.getSocket() );
    }

    @Test
    public void setRequestLineAndGetIt() throws Exception {
        String method = "GET";
        String path = "/test/mock/path";
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);

        request.setMethod(method);
        assertEquals(HttpMethod.valueOf(method), request.getMethod());

        request.setPath(path);
        assertEquals(path, request.getPath());
    }

    @Test
    public void addHeaderAndGetIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);
        request.setSocket(socket);

        String dummyHeader = "dummy header!";
        request.addHeader(dummyHeader);
        List<String> headers = new ArrayList<String>();
        assertEquals( 0 , headers.size() );

        headers = request.getHeaders();
        assertEquals( 1 , headers.size() );
        assertEquals( dummyHeader , headers.get(0) );
    }

    @Test
    public void setDocRootAndGetFullPath() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);

    }

    @Test
    public void addParmsAndGetThem() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);
        request.setSocket(socket);

        String[][] parms = new String[2][2];
        parms[0] = new String[]{ "label1", "value1" };
        parms[1] = new String[]{ "label2", "value2" };

        request.setParms(parms);
        assertEquals(parms[0][0], request.getParms()[0][0]);
        assertEquals(parms[0][1], request.getParms()[0][1]);
        assertEquals(parms[1][0], request.getParms()[1][0]);
        assertEquals(parms[1][1], request.getParms()[1][1]);
    }

    @Test
    public void addContentAndHasGetLengthIt() throws Exception {
        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);
        request.setSocket(socket);

        assertEquals(false, request.hasContent());
        assertEquals(0, request.getContentLength());

        String content = "I love content!";
        request.setContent(content);
        assertEquals(true, request.hasContent());
        assertEquals(content, request.getContent());
        assertEquals(15, request.getContentLength());
    }
}