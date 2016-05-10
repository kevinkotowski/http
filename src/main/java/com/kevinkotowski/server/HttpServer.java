package com.kevinkotowski.server;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements Server{
    private int port = 0;
    private boolean isListening = false;
    private IONetwork network;

    HttpServer(int port) throws IOException {
        this.port = port;
        this.network = new HttpNetwork(port);
    }

    HttpServer(IONetwork network) {
        this.network = network;
    }

    public void listen() throws IOException {
        this.isListening = true;
        System.out.println( this.status() );

        while ( this.network.hasNext() ) {
            IORequest request = this.network.next();

            System.out.println(request.getMethod() + " " +
                    request.getPath());

            if (request.getResponseCode().equals("200")) {
                HttpHandler handler = new HttpHandler();
                IOResponse response = handler.handle(request);
                if (response.getBody() != null) {
                    this.network.writeln( response.getBody() );
                }
            }
            this.close();
        }
    }

    public void close() throws IOException {
        this.isListening = false;
        this.network.closeOut();
    }

    public String status() {
        String message = this.isListening ? "Listening" : "Waiting";
        message += " on port " + Integer.toString(this.port);
        return message;
    }
}
