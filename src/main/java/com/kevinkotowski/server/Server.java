package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public interface Server {
    public void listen() throws IOException;
    public void close() throws IOException;
    public String status();
}