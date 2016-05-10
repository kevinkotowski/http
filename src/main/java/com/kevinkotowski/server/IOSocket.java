package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/9/16.
 */
public interface IOSocket {
    public boolean hasNext();
    public String nextLine();
    public int getPort();
    public void close() throws IOException;
}
