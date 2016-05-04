package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer {
    private int portNumber = 0;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream httpIn;
    private PrintStream httpOut;
    private InputStream fileIn;
    private PrintStream fileOut;
    private FileServer fileServer;
    private String filePath;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java HttpServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        HttpServer httpServer = new HttpServer(portNumber);
        httpServer.run();
    }

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

    public void setFile(String path) {
        this.filePath = path;
    }

    public void run() {
        try {
            this.httpOut.write("Welcome!\n".getBytes(UTF_8));
            while (this.httpIn.available() > 0) {
                this.httpOut.write(this.httpIn.read());
            }
            // TODO: Use input as pathname for FileServer and echo it.
            this.httpOut.write("\nGoodbye.".getBytes(UTF_8));
            this.httpIn.close();
            this.httpOut.close();
        } catch (IOException e) {
            System.out.println("Exception caught echoing in to out.");
            System.out.println(e.getMessage());
        }
    }
}
