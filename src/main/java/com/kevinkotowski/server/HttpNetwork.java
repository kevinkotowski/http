package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpNetwork implements IONetwork {
    private int port = -1;
    private ServerSocket serverSocket;

    public HttpNetwork(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + port + " to listen for a connection");
            System.out.println(e.getMessage());
        }
    }

    public IORequest next() throws IOException {
        InputStream in;
        IOSocket socket;
        Scanner scanner;

        socket = new HttpSocket( this.serverSocket.accept() );
        in = socket.getInputStream();

        scanner = new Scanner(in, "UTF8");
        HttpRequest request = new HttpRequest();
        request.setSocket(socket);
        String headerLine = new String();

        // TODO: get initial line
        request.handleRequestLine( scanner.nextLine() );

        // TODO: get header lines
        while ( (headerLine = scanner.nextLine()).length() > 0 ) {
            request.addHeader(headerLine);
        }

        // TODO: get optional content
//        if ( this.scanner.hasNextLine() ) {
//            response.handleOptionalContent( this.scanner.nextLine() );
//        }
        // if any additional data, give to response.handleOptionalData

        return request;
    }

    public int getPort() {
        return this.port;
    }

}
