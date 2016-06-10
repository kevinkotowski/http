package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class MockPostware implements IHPostware {
    public IHResponse transform(IHResponse response) {
        response.setBody("postware body");
        return response;
    }
}
