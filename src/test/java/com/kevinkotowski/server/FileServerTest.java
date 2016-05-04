package com.kevinkotowski.server;

import org.junit.Test;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertTrue;

public class FileServerTest {
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
    public void runWithInputStream() throws Exception {
        String mockFile = "I'm pretending to be a file.";
        this.in = ioInput(mockFile);
//        String path = "/Users/kevinkotowski/Development/sites/http/index.htm";
//        this.in = new FileInputStream(path);
        FileServer fileServer = new FileServer(this.in, this.out);
        fileServer.run();
        System.out.print(ioOutput(this.out));
        assertTrue(ioOutput(this.out).contains(mockFile));
    }
}