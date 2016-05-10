package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IOHandler {
    public IOResponse handle(IORequest request) throws IOException;
}
