package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _LoggerTest {
    @Test
    public void writeln() throws Exception {
        IOFile file = new MockFile("/mock/logs");
        IHLogger logger = new HttpLogger(file);
        logger.writeln("Log message");
        // this looks to not test anything, but logger uses only native
        //     java.io.File functions with no custom logic
    }

}