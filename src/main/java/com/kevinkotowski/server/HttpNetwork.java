package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpNetwork implements IHNetwork {
    private int port = -1;
    private ServerSocket serverSocket;
    private IHRequestParser parser;

    public HttpNetwork(int port) {
        this.port = port;
        this.parser = new HttpRequestParser();

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + port + " to listen for a connection");
            System.out.println(e.getMessage());
        }
    }

    public IHRequest next() throws IOException {
        IOSocket socket = new HttpSocket( this.serverSocket.accept() );
        IHRequest request = this.parser.parse(socket);
        return request;
    }

    public int getPort() {
        return this.port;
    }

}
