package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/2/16.
 */
public interface IHRequestParser {
    public IORequest parse(IOSocket socket) throws IOException;
}
