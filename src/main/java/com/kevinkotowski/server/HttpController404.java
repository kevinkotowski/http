package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpController404 implements IHController {
    public IOResponse execute(IORequest request) {
        IOResponse response = HttpResponseFactory.create(request);

        response.setResponseCode("404");
        response.setResponseReason("File not found (kk)");
        return response;
    }
}
