package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class HttpTransformREDIRECT implements IHTransformer {
    String matchPath;
    String redirectPath;
    String host;
    boolean isPathMatch = false;

    public HttpTransformREDIRECT(String matchPath, String redirectPath) {
        this.matchPath = matchPath;
        this.redirectPath = redirectPath;
    }

    public IHRequest transformRequest(IHRequest request) {
        this.isPathMatch = request.getPath().equals(this.matchPath);
        this.host = request.getHost();
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        if (this.isPathMatch) {
            response.setResponseCode("302");
            response.setResponseReason("Redirect (kk)");
            response.addHeader("Location: " + this.host + this.redirectPath);
        }
        return response;
    }
}
