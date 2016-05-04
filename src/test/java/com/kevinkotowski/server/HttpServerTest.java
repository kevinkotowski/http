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

    private String command = "GET";
    private String filePath = "src/test/java/com/kevinkotowski/server/test.htm";

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
    public void plainEchoViaCat() throws Exception {
        String message = this.command + " " + this.filePath;
        InputStream in = ioInput(message);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServer httpServer = new HttpServer(in, out);

        httpServer.cat();
        httpServer.close();
        System.out.println(ioOutput(out));
        assertTrue(ioOutput(out).contains(message));
    }

    @Test
    public void manualSetGetFile() throws Exception {
        String message = this.command + " " + this.filePath;
        InputStream in = ioInput(message);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServer httpServer = new HttpServer(in, out);

        httpServer.setFilePath(this.filePath);
        assertEquals(this.filePath, httpServer.getFilePath());
    }

    @Test
    public void scanFile() throws Exception {
        String message = this.command + " " + this.filePath;
        InputStream in = ioInput(message);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServer httpServer = new HttpServer(in, out);

        httpServer.scan();
        httpServer.close();
        System.out.println(ioOutput(out));
        assertTrue(ioOutput(out).contains(this.command));
        assertTrue(ioOutput(out).contains(this.filePath));
    }
}