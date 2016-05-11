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
    private String filePath = "/src/test/java/com/kevinkotowski/server/test.htm";
    private String mockFile = "<html>\n<p>Mock files rock!</p>\n</html>";

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
    public void plainEchoOfHttpInput() throws Exception {
//        String operation = "GET " + this.filePath;
//        InputStream in = ioInput(operation);
//        MockSocket socket = new MockSocket(3210);

//        IOSockets sockets = new MockSockets();
//        HttpServer httpServer = new HttpServer(sockets);
//        httpServer.listen();
//
//        httpServer.echo();
//        httpServer.close();
//        System.out.println(socket.getOut());
//        assertTrue(socket.getOut().toString("UTF8").contains(operation));
    }

    @Test
    public void scanMockFileSuccessfully() throws Exception {
//        String message = this.command + " " + this.filePath;
////        message += " " + this.command + " boogy";
//        InputStream in = ioInput(message);
//        MockSocket socket = new MockSocket(3210);
//        HttpServer httpServer = new HttpServer(socket);
//
//        httpServer.listen();
//        System.out.println( socket.getOut() );
////        assertTrue(socket.getOut().toString("UTF8").contains(this.command));
////        assertTrue(socket.getOut().toString("UTF8").contains(this.filePath));
////        assertTrue(socket.getOut().toString("UTF8").contains("Kevin was here!"));
    }
}