package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements Server{
    private int portNumber = 0;
    private boolean isListening = false;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream httpIn;
    private PrintStream httpOut;
    private FileInputStream fileIn = null;

    HttpServer(int portNumber) {
        this.portNumber = portNumber;

        try {
            this.serverSocket = new ServerSocket(this.portNumber);
            this.clientSocket = this.serverSocket.accept();
            this.httpOut =
                    new PrintStream(clientSocket.getOutputStream(), true);
            this.httpIn = clientSocket.getInputStream();

        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    HttpServer(InputStream httpIn, OutputStream httpOut) {
        this.httpIn = httpIn;
        this.httpOut = new PrintStream(httpOut, true);
    }

    public void listen() throws IOException {
        this.isListening = true;
        this.httpOut.write(this.status().getBytes(UTF_8));

        HttpScanner scanner = new HttpScanner(this.httpIn);
        if ( scanner.hasNext() ) {
            // TODO: needs error handling
            HttpRequest httpRequest = scanner.next();
            httpRequest.execute(this.httpOut);
        }
        // TODO: remove when it is a background process
        this.close();
    }

    public void close() throws IOException {
        this.isListening = false;
        this.httpOut.write("Closing...\n".getBytes(UTF_8));
        this.httpIn.close();
        this.httpOut.close();
    }

    public String status() {
        String message = new String();
        message = this.isListening ? "Listening" : "Waiting";
        message += " on port " + Integer.toString(this.portNumber);
        message += "\n";
        return message;
    }

    public void echo() throws IOException {
        while (this.httpIn.available() > 0) {
            this.httpOut.write(this.httpIn.read());
        }
        this.httpOut.write("\n".getBytes("UTF8"));
    }
}
