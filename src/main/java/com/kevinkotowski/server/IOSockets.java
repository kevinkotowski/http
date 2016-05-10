package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public interface IOSockets {
    public void setIn(ByteArrayInputStream inStream);
    public OutputStream getOut();
    public boolean hasNext();
    public IORequest next();
    public int getPort();
    public void close() throws IOException;
    public void closeOut() throws IOException;
}
