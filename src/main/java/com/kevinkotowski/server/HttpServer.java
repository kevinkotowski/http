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
    private FileInputStream fileIn = null;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java HttpServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        HttpServer httpServer = new HttpServer(portNumber);
        httpServer.run();
    }{}

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

    public void close() throws IOException {
        this.httpIn.close();
        this.httpOut.close();
    }

    public void echo() throws IOException {
        while (this.httpIn.available() > 0) {
            this.httpOut.write(this.httpIn.read());
        }
        this.httpOut.write("Goodbye.".getBytes(UTF_8));
    }

    private void echoOperation(String[] operation) throws IOException {
        this.httpOut.write(operation[0].getBytes(UTF_8));
        this.httpOut.write(" ".getBytes(UTF_8));
        this.httpOut.write(operation[1].getBytes(UTF_8));
        this.httpOut.write("\n".getBytes(UTF_8));
    }

    private void executeOperation(String[] operation) throws IOException {
        if (!validOperation(operation)) {
            throw new RuntimeException("Invalid command: "
                    + operation[0]);
        }
        this.echoOperation(operation);

        switch (operation[0]) {
            case "GET":
                this.executeGET(operation[1]);
                break;
            default: new Exception("Critical error, validated operation ");
        }
    }

    private void executeGET(String filePath) throws IOException {
        if (this.fileIn == null) {
            this.fileIn = new FileInputStream(filePath);
        }

        while (this.fileIn.available() > 0) {
            this.httpOut.write(this.fileIn.read());
        }
    }

    public void run() throws IOException {
        // TODO: Remove this logic after development.
        // this is just a pointer to whichever function runs the show
        this.echo();
    }

    public void scan() throws IOException {
        // input is expected to follow this pattern:
        //      "[command] [operand] ..."

        Scanner scanner = new Scanner(this.httpIn, "UTF8");
        String operation[] = new String[2];
        // operation[0] holds the command
        // operation[1] holds the operand

        while (scanner.hasNext()) {
            operation[0] = scanner.next();

            if (scanner.hasNext()) {
                operation[1] = scanner.next();
                this.executeOperation(operation);
            } else {
                throw new RuntimeException("Missing operand for "
                        + operation[0]);
            }
            operation[0] = null;
            operation[1] = null;
        }
    }

//    public void setFileStream(FileInputStream fileIn) {
//        this.fileIn = fileIn;
//    }

    private boolean validOperation(String[] operation) {
        switch (operation[0]) {
            case "GET": break;
            default: return false;
        }
        return true;
    }
}
