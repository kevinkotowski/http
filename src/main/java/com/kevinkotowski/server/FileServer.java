package com.kevinkotowski.server;

import java.net.*;
import java.io.*;

/**
 * Created by kevinkotowski on 4/29/16.
 */
public class FileServer {
    private int portNumber = 0;
    private ServerSocket serverSocket;
    private Socket clientSocket;
//    private Protocol protocol;
//    private ProtocolIn in;
//    private ProtocolOut out;
    private InputStreamReader in;
//    private InputStreamReader in;
    private PrintStream out;
    private String path;
    private InputStream inStream;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java FileServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        FileServer fileServer = new FileServer(portNumber);
        fileServer.run();
    }

    public FileServer (int portNumber) {
        this.portNumber = portNumber;

        try {
            this.serverSocket = new ServerSocket(this.portNumber);
            this.clientSocket = this.serverSocket.accept();
            this.out =
                    new PrintStream(clientSocket.getOutputStream(), true);
//            this.in = new BufferedReader(
//                    new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println("Exception caught when trying to initialize on "
                    + " port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public FileServer (InputStream in, OutputStream out) {
        this.in = new InputStreamReader(in);
        this.out = new PrintStream(out);
    }

    public String getPath(String path) {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void run() {
//        Route route = getRoute;
//        FileContent content = getContent(route);
//        handleContent(content);
        try {
            // Initiate conversation with client
            this.out.println("Working Directory = " +
                    System.getProperty("user.dir"));

            this.setPath("/Users/kevinkotowski/Development/sites/http/index.htm");

            File file = new File(this.path);

            FileReader fr = new FileReader(file);
            char [] a = new char[120];
            fr.read(a); // reads the content to the array
            for(char c : a)
                this.out.print(c); //prints the characters one by one
            fr.close();

            this.out.println("Goodbye, Kevin.");

        } catch (IOException e) {
            System.out.println("Exception caught when trying to write to port "
                    + portNumber + " or failure reading file");
            System.out.println(e.getMessage());
        }
    }

    public void runInputStream() {
        try {
            // Initiate conversation with client
            this.out.println("Working Directory = " +
                    System.getProperty("user.dir"));

            File file = new File("/Users/kevinkotowski/Development/sites/http/index.htm");

            FileReader fr = new FileReader(file);
            char [] a = new char[120];
            fr.read(a); // reads the content to the array
            for(char c : a)
                this.out.print(c); //prints the characters one by one
            fr.close();

            this.out.println("Goodbye, Kevin.");

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
