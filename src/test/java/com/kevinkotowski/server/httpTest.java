package com.kevinkotowski.server;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class httpTest {
    @Test
    public void listenWithHttpOnNetwork() throws Exception {
        int port = 3210;
//        String file = "GET src/test/java/com/kevinkotowski/server/test.htm";
//        ByteArrayInputStream in = new ByteArrayInputStream( file.getBytes(UTF_8) );

        HttpSockets sockets = new HttpSockets(port);
        assertEquals( port, sockets.getPort() );

        System.out.println("...before new http");
        http http = new http(sockets);
        System.out.println("...after new http");
    }

//    @Test
//    public void listenWithHttpServerMockNetwork() throws Exception {
//        MockNetwork mockNetwork = new MockNetwork();
//
//        System.out.println("...before new http");
//        http http = new http(mockNetwork);
//        System.out.println("...after new http");
//    }
}