package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kevinkotowski on 5/9/16.
 */
public class HttpSocket {
    private int port = -1;
    private ServerSocket serverSocket;
//    private Socket socket;
    private InputStream in;
//    private OutputStream out;
//    private Scanner scanner;

    public HttpSocket(int port) {
        this.port = port;
        this.connect();
    }

    public int getPort() {
        return this.port;
    }

    private void connect() {
//        try {
//           this.serverSocket = new ServerSocket(this.port);
//        }
    }

    public IORequest accept() {

        // accept off of ServerSocket
        // put new Socket in IORequest
        return null;

    }

    public void close() throws IOException {

    }
}
