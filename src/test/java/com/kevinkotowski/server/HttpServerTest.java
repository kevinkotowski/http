package com.kevinkotowski.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServerTest {
    private InputStream in;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public static InputStream ioInput(String input) {
        return new ByteArrayInputStream( input.getBytes(UTF_8) );
    }

    public static String ioOutput(ByteArrayOutputStream out) {
        try{
            return out.toString("UTF8");
        } catch (IOException ioe) {
            return String.valueOf(ioe);
        }
    }

    @Test
    public void plainEchoViaRun() throws Exception {
        String message = "Kevin was here.";
        this.in = ioInput(message);
        HttpServer httpServer = new HttpServer(this.in, this.out);

        httpServer.run();
        System.out.println(ioOutput(this.out));
        assertTrue(ioOutput(this.out).contains(message));
    }

    @Test
    public void setFile() throws Exception {

    }

}