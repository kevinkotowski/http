package com.kevinkotowski.server;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _HashAlgoSHA1Test {
    @Test
    public void staticHashSHA1() throws Exception {
        String text = "sample text for SHA1 to convert";
        String correctHash = "5e6a4c69c960170ccb97c325ac62b23d498bb08d";

        assertEquals( correctHash, HttpHashAlgoSHA1.hash(text) );
    }

}
