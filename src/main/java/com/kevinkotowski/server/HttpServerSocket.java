package com.kevinkotowski.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class HttpServerSocket implements IOServerSocket {
    ServerSocket serverSocket;

    public HttpServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + port + " to listen for a connection");
            System.out.println(e.getMessage());
        }
    }

    public IOSocket accept() throws IOException {
        return new HttpSocket(this.serverSocket.accept());
    }
}
