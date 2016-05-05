package com.kevinkotowski.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class httpTest {
    @Test
    public void launchHttpServer() throws Exception {
        InputStream in = new ByteArrayInputStream( "".getBytes(UTF_8) );
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpServer httpServer = new HttpServer(in, out);
        http http = new http(httpServer);

        System.out.println( out.toString("UTF8") );
        assertTrue( out.toString("UTF8").contains("Listening") );
    }
}