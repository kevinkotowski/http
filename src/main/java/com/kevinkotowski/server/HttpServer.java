package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements Server{
    private int port = 0;
    private String docRoot = null;
    private boolean isListening = false;
    private IONetwork network;

    HttpServer(int port, String docRoot) throws IOException {
        this.port = port;
        this.docRoot = docRoot;
        this.network = new HttpNetwork(port);
    }

    HttpServer(IONetwork network, String docRoot) {
        this.docRoot = docRoot;
        this.network = network;
    }

    public void listen() throws IOException {
        this.isListening = true;
        System.out.println( this.status() );

        while ( isListening ) {
            IORequest request = this.network.next();

            HttpHandler handler = new HttpHandler(docRoot);
            IOResponse response = handler.handle(request);

            response.run();
        }
    }

    public void close() throws IOException {
        this.isListening = false;
        this.network = null;
    }

    public String status() {
        String message = this.isListening ? "Listening" : "Waiting";
        message += " on port " + Integer.toString(this.port);
        message += " for dir " + (this.docRoot);
        return message;
    }
}
