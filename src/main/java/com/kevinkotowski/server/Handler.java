package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface Handler {
    public IOResponse handle(IORequest request) throws IOException;
}
