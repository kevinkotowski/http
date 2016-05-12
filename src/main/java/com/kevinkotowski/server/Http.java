package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class http {
    Server server;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java http <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        Server httpServer = new HttpServer(portNumber);
        httpServer.listen();
    }{}

    http( Server server ) throws IOException {
        this.server = server;
        server.listen();
    }

    public void stop() throws IOException {
        server.close();
    }
}
