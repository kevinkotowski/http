package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/12/16.
 */
public class _FileTest {
    // NOTE: Most of this class is *exactly* java.io.File. This test will
    //       cover listFiles(), because that is original code. It does not
    //       cover writeln(), because there is a series of java.io classes
    //       without material custom logic. Mocking out each class in the
    //       chain would really just serve to test java.io.

    @Test
    public void listFiles() throws Exception {
        IOFile file = new HttpFile("/");
        IOFile[] files = file.listFiles();
        assertTrue(files.length > 5);
    }

}