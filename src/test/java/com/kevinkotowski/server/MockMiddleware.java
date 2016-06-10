package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class MockMiddleware implements IHMiddleware {
    public IHRequest transform(IHRequest request) {
        request.setPath("/middleware/path");
        return request;
    }
}
