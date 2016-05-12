package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public interface IONetwork {
    public IORequest next() throws IOException;
    public int getPort();
}
