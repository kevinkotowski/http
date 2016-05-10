package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class http {
    HttpServer server = null;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java http <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        HttpServer httpServer = new HttpServer(portNumber);
        httpServer.listen();
    }{}

    http( IONetwork network ) throws IOException {
        this.server = new HttpServer(network);
        server.listen();
    }

    public void stop() throws IOException {
        server.close();
    }
}
