package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpController405 implements IHController {
    public IOResponse execute(IORequest request) {
        IOResponse response = HttpResponseFactory.create(request);

        response.setResponseCode("405");
        response.setResponseReason("Invalid Method (kk)");
        return response;
    }
}
