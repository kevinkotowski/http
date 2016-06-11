package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class MockMiddleware extends HttpMiddleware {
    public IHRequest transformRequest(IHRequest request) {
        request.setPath("/middleware/path");
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        response.setBody("middleware body");
        return response;
    }
}
