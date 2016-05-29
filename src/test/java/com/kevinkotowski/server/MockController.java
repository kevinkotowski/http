package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/29/16.
 */
public class MockController implements IHController {
    public IOResponse execute() {
        return new MockResponse();
    }
}
