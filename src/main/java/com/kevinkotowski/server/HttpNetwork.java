package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpNetwork implements IHNetwork {
    private IOServerSocket serverSocket;
    private IHRequestParser parser;

    public HttpNetwork(IOServerSocket serverSocket, IHRequestParser parser) {
        this.parser = parser;
        this.serverSocket = serverSocket;
    }

    public IHRequest next() throws IOException {
        IOSocket socket = this.serverSocket.accept();
        IHRequest request = this.parser.parse(socket);
        return request;
    }
}
