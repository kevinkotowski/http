package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _ArgumentsTest {
    @Test
    public void parseParametersInOrder() throws Exception {
        String port = "5000";
        String docroot = "/docroot";
        String[] args = { "-p", port, "-d", docroot };
        String[] results = HttpArguments.parse(args);

        assertEquals(port, results[0]);
        assertEquals(docroot, results[1]);
    }

    @Test
    public void parseParametersReverseOrder() throws Exception {
        String port = "5000";
        String docroot = "/docroot";
        String[] args = { "-d", docroot, "-p", port };
        String[] results = HttpArguments.parse(args);

        assertEquals(port, results[0]);
        assertEquals(docroot, results[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNoParametersThrowUsage() throws Exception {
        String[] args = {};
        String[] results = HttpArguments.parse(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseParametersMissingPort() throws Exception {
        String[] args = { "-d", "/docroot", "-p" };
        String[] results = HttpArguments.parse(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseParametersMissingDocroot() throws Exception {
        String[] args = { "-d", "-p", "5000", };
        String[] results = HttpArguments.parse(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseParametersBadOption() throws Exception {
        String[] args = { "-d", "/docroot", "-x", "what?", "-p", "5000", };
        String[] results = HttpArguments.parse(args);

        assertEquals("5000", results[0]);
        assertEquals("/docroot", results[1]);
    }
}