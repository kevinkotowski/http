package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public interface IOSocket {
    public InputStream getInputStream() throws IOException;
    public OutputStream getOutputStream() throws IOException;
    public void close() throws IOException;
}
