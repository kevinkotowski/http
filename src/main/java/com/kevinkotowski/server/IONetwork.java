package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IONetwork {
    public int getPort();
    public boolean hasNext();
    public IORequest next();
    public void write(byte b) throws IOException;
    public void writeln(String string) throws IOException;
    public void close() throws IOException;
    public void closeOut() throws IOException;
}
