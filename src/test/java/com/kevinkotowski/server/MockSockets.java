package com.kevinkotowski.server;

import javax.net.SocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class MockSockets implements IOSockets {
    private int port = -1;
    private InputStream in;
    private OutputStream out;
    private HttpScanner scanner;

    public MockSockets(int port) throws IOException {
        this.port = port;

//        this.out = new PrintStream(clientSocket.getOutputStream(), true);
        this.out = new ByteArrayOutputStream();
        this.in = new ByteArrayInputStream(
            "mock input stream is\ncool like a bow tie".getBytes(UTF_8) );
        this.scanner = new HttpScanner(this.in);
    }

    public void setIn(ByteArrayInputStream inStream) {
        this.in = inStream;
    }

//    public InputStream getIn() {
//        return this.in;
//    }

    public boolean hasNext() {
        return this.scanner.hasNext();
    }

    public IORequest next() {
        return this.scanner.next();

    }

    public OutputStream getOut() {
        return this.out;
    }

    public int getPort() {
        return this.port;
    }

    public void close() throws IOException {
        this.in.close();
        this.out.close();
    }

    public void closeOut() throws IOException {
        this.out.close();
    }
}
