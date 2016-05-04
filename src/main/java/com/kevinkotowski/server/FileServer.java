package com.kevinkotowski.server;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 4/29/16.
 */
public class FileServer {
    private InputStream in;
//    private PrintStream out;
    private String path;

    public FileServer (String path) {
        this.path = path;
        try {
            this.in = new FileInputStream(path);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to read file." );
            System.out.println(e.getMessage());
        }
//        this.out = new PrintStream(System.out);
    }

    public FileServer (InputStream in) {
        this.in = in;
//        this.out = new PrintStream(out);
    }

    public String readFile() {
        String returnFile = null;
        try {
//            this.out.println("Working Directory = " +
//                    System.getProperty("user.dir"));
            int fileSize = this.in.available();
            byte[] content = new byte[fileSize];
            this.in.read(content);
            returnFile = new String(content, UTF_8);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to read file." );
            System.out.println(e.getMessage());
        }
        return returnFile;
    }

    public int available() throws IOException {
        return this.in.available();
    }

    public int read() throws IOException {
        int returnByte = 0;
        if (this.in.available() > 0) {
            returnByte = this.in.read();
        }
        return returnByte;
    }
}
