package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class HttpTransformREDIRECT implements IHTransformer {
    String originalPath;
    String redirectPath;
    boolean isPathMatch = false;

    public HttpTransformREDIRECT(String originalPath, String redirectPath) {
        this.originalPath = originalPath;
        this.redirectPath = redirectPath;
    }

    public IHRequest transformRequest(IHRequest request) {
        this.isPathMatch = request.getPath().equals(this.originalPath);
        request.setPath(this.redirectPath);
        request.setContent(request.getContent() + ".");
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        if (this.isPathMatch) {
            response.setResponseCode("302");
            response.setResponseReason("Redirect (kk)");
            response.addHeader("Location: " + this.redirectPath);
        }
        return response;
    }
}
