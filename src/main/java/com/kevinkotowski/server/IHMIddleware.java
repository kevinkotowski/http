package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public interface IHMiddleware {
    public IHRequest transform(IHRequest request);
}
