package com.kevinkotowski.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpScannerTest {
    @Test
    public void checkForMoreInputWhenEmpty() throws Exception {
        InputStream inEmpty = new ByteArrayInputStream("".getBytes(UTF_8));
        HttpScanner scanner = new HttpScanner(inEmpty);

        assertFalse(scanner.hasNext());
    }

    @Test
    public void getMoreInputCommands() throws Exception {
        String operation = "GET c://boogy/file.html";
        InputStream in = new ByteArrayInputStream( operation.getBytes(UTF_8) );

        HttpScanner scanner = new HttpScanner(in);
        assertTrue( scanner.hasNext() );
        HttpRequest request = scanner.next();
        assertEquals( "GET", request.getMethod() );
    }
}