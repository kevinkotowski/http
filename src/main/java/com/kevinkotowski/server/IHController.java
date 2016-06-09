package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHController {
    public IHResponse execute(IHRequest request) throws Exception;
}
