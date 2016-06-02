package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHController {
    public IOResponse execute(IORequest request) throws IOException;
}
