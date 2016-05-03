package com.kevinkotowski.server;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class FileProtocolTest {
    @Test
    public void readFile() throws Exception {
        File file = new File("/Users/kevinkotowski/Development/sites/http/index.htm");
        FileProtocol fp = new FileProtocol(file);
        char[] content = new char[120];

        fp.read(content);
        for(char c : content) {
            System.out.print(c);
        }
    }
}