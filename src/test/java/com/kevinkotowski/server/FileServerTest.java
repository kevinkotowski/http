package com.kevinkotowski.server;

import org.junit.Test;

import javax.net.SocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
    public void runWithFileReader() throws Exception {
        this.in = ioInput("");
        FileServer fileServer = new FileServer(this.in, this.out);
        fileServer.run();
        assertTrue(ioOutput(this.out).contains("Goodbye, Kevin."));
    }
    @Test
    public void runWithSocketOutputStream() throws Exception {
//        this.in = ioInput("");
//        ServerSocket serverSocket = new ServerSocket(3210);
        FileServer fileServer = new FileServer(this.in, this.out);
        fileServer.run();
        assertTrue(ioOutput(this.out).contains("Goodbye, Kevin."));
    }
    @Test
    public void runWithInputStream() throws Exception {
        this.in = ioInput("");
        FileServer fileServer = new FileServer(this.in, this.out);
        fileServer.runInputStream();
        System.out.print(ioOutput(this.out));
        assertTrue(ioOutput(this.out).contains("Goodbye, Kevin."));
    }
}