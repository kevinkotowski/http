package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpNetwork implements IONetwork {
    private int port;
    private IOSockets sockets;

    public HttpNetwork(int port) throws IOException {
        this.port = port;
        this.sockets = new HttpSockets(port);
        System.out.println("Waiting...");
    }

    public int getPort() {
        return this.port;
    }

    public boolean hasNext() {
        return this.sockets.hasNext();
    }

    public IORequest next() {
        return this.sockets.next();
//        return new HttpRequest("GET", "src/test/java/com/kevinkotowski/server/test.htm");
    }

    public void write(byte b) throws IOException {
        try {
            this.sockets.getOut().write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeln(String string) throws IOException {
        byte[] bytes = (string + "\n").getBytes("UTF8");
        this.sockets.getOut().write(bytes);
        this.sockets.getOut().flush();
    }

    public void closeOut() throws IOException {
       this.sockets.closeOut();
    }
    public void close() throws IOException {
        System.out.println("Closing...");
        this.sockets.close();
    }
}
