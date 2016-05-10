package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpSockets implements IOSockets {
    private int port = -1;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private PrintStream out;
    private HttpScanner scanner;

    public HttpSockets(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(this.port);
            this.clientSocket = this.serverSocket.accept();
            this.openOut();
            this.in = clientSocket.getInputStream();
            this.scanner = new HttpScanner(this.in);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public void setIn(ByteArrayInputStream inStream) {
        this.in = inStream;
    }

//    public InputStream getIn() {
//        return this.httpIn;
//    }

//    public HttpScanner getScanner() {
//        return this.scanner;
//    }

    public boolean hasNext() {
        return this.scanner.hasNext();
    }

    public IORequest next() {
        return this.scanner.next();

    }

    public PrintStream getOut() {
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

    public void openOut() throws IOException {
        this.out = new PrintStream(clientSocket.getOutputStream(), true);
    }
}
