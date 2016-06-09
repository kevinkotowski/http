package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IHHandler {
    public IHResponse handle(IHRequest request) throws IOException;
}
