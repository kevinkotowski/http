package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockHandler implements IOHandler {
    public IOResponse handle(IORequest request) {
        return new MockResponse();
    }
}
