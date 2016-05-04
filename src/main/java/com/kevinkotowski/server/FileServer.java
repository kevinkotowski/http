package com.kevinkotowski.server;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 4/29/16.
 */
public class FileServer {
    private InputStream in;
    private PrintStream out;
    private String path;

    public FileServer (InputStream in, OutputStream out) {
        this.in = in;
        this.out = new PrintStream(out);
    }

    public void run() {
        try {
//            this.out.println("Working Directory = " +
//                    System.getProperty("user.dir"));

            int fileSize = this.in.available();
            byte[] content = new byte[fileSize];
            this.in.read(content);
            this.out.print( new String(content, UTF_8) );
            this.in.close();

        } catch (IOException e) {
            System.out.println("Exception caught when trying to input "
                    + path );
            System.out.println(e.getMessage());
        }
    }

}
