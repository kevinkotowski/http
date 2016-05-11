package com.kevinkotowski.server;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements Server{
    private int port = 0;
    private boolean isListening = false;
    private IOSockets sockets;

    HttpServer(int port) throws IOException {
        this.port = port;
        this.sockets = new HttpSockets(port);
    }

    HttpServer(IOSockets sockets) {
        this.sockets = sockets;
    }

    public void listen() throws IOException {
        this.isListening = true;
        System.out.println( this.status() );

        while ( isListening ) {
            IORequest request = this.sockets.next();

            System.out.println(request.getMethod() + " " +
                    request.getPath());

            HttpHandler handler = new HttpHandler();
            IOResponse response = handler.handle(request);

            response.run();
        }
    }

    public void close() throws IOException {
        this.isListening = false;
        this.sockets = null;
    }

    public String status() {
        String message = this.isListening ? "Listening" : "Waiting";
        message += " on port " + Integer.toString(this.port);
        return message;
    }
}
