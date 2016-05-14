package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class _httpTest {
    @Test
    public void validateGoodArguments() throws Exception {
        String port = "5000";
        String dir = "/mock/dir";
        String[] response = new String[2];
        response = http.handleArguments( new String[]{"-p", port, "-d", dir} );
        assertEquals( port, response[0] );
        assertEquals( dir, response[1] );
    }

    @Test(expected = RuntimeException.class)
    public void validateBadArgumentCount() throws Exception {
        String port = "5000";
        String dir = "/mock/dir";
        String[] response = new String[2];
        response = http.handleArguments( new String[]{"-p", "-d", dir} );
    }

    @Test(expected = RuntimeException.class)
    public void validateBadPortArgument() throws Exception {
        String port = "5000";
        String dir = "/mock/dir";
        String[] response = new String[2];
        response = http.handleArguments( new String[]{"p", port, "-d", dir} );
    }

    @Test
    public void createServerAndListen() throws Exception {
        Server server = new MockServer();
        http http = new http(server);
        assertTrue( server.status().contains("listening") );
    }
}