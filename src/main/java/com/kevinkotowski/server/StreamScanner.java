package com.kevinkotowski.server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public interface StreamScanner {
    public boolean hasNext();
    public HttpRequest next();
}
