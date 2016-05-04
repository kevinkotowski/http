package com.kevinkotowski.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
//    private FileServer fileServer;
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

    public void run() throws IOException {
        // TODO: Remove this logic after development.
        // this is just a pointer to whichever function runs the show
        this.cat();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void cat() throws IOException {
//        this.httpOut.write("Welcome!\n".getBytes(UTF_8));
        while (this.httpIn.available() > 0) {
            this.httpOut.write(this.httpIn.read());
        }
//        this.httpOut.write("\nGoodbye.".getBytes(UTF_8));
    }

    public void scan() throws IOException {
        // input is expected to follow this pattern:
        //      "[command] [operand] ..."

        Scanner scanner = new Scanner(this.httpIn, "UTF8");
        String operation[] = new String[2];
        // operation[0] = command
        // operation[1] = operand

        if (scanner.hasNext()) {
            operation[0] = scanner.next();
        }
        if (scanner.hasNext()) {
            operation[1] = scanner.next();
            this.executeOperation(operation);
        }
        operation[0] = null;
        operation[1] = null;
    }

    private void executeOperation(String[] operation) throws IOException {
        this.httpOut.write(operation[0].getBytes(UTF_8));
        this.httpOut.write(" ".getBytes(UTF_8));
        this.httpOut.write(operation[1].getBytes(UTF_8));
    }

    public void close() throws IOException {
        this.httpIn.close();
        this.httpOut.close();
    }

}
