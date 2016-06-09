package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockHandler implements IHHandler {
    public IHResponse handle(IHRequest request) {
        return new MockResponse();
    }
}
