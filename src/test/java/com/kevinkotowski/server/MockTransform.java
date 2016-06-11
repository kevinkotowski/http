package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class MockTransform implements IHTransformer {
    String path;
    String newText;
    boolean isPathMatch = false;

    public MockTransform(String path, String newText) {
        this.path = path;
        this.newText = newText;
    }

    public IHRequest transformRequest(IHRequest request) {
        this.isPathMatch = request.getPath().equals(this.path);
        request.setContent(this.newText);
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        if (this.isPathMatch) {
            response.setBody(newText);
        }
        return response;
    }
}
