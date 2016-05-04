package com.kevinkotowski.server;

import org.junit.Test;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertTrue;

public class FileServerTest {
    private InputStream in;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public static InputStream ioInput(String input) {
        return new ByteArrayInputStream( input.getBytes(UTF_8) );
    }

//    public static String ioOutput(ByteArrayOutputStream out) {
//        try{
//            return out.toString("UTF8");
//        } catch (IOException ioe) {
//            return String.valueOf(ioe);
//        }
//    }

    @Test
    public void runWithInputStream() throws Exception {
        String mockFile = "I'm pretending to be a file.";
        this.in = ioInput(mockFile);
        FileServer fileServer = new FileServer(this.in);
        String file = null;

        file = fileServer.readFile();
        System.out.print(file);
        assertTrue(file.contains(mockFile));
    }

    @Test
    public void runWithFileInputStream() throws Exception {
        String path = "src/test/java/com/kevinkotowski/server/test.htm";
        this.in = new FileInputStream(path);
        FileServer fileServer = new FileServer(this.in);
        String file = null;

        file = fileServer.readFile();
        System.out.print(file);
        assertTrue(file.contains("Kevin was here!"));
    }

    @Test
    public void readByteByByte() throws Exception {
        String path = "src/test/java/com/kevinkotowski/server/test.htm";
        this.in = new FileInputStream(path);
        FileServer fileServer = new FileServer(this.in);

        while (fileServer.available() > 0) {
            this.out.write(fileServer.read());
        }
        System.out.print(this.out);
//        assertTrue(this.out.toString("UTF8").contains("Kevin was here!"));
    }
}