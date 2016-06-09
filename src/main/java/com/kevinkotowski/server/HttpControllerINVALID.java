package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerINVALID implements IHController {
    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());

        response.setResponseCode("405");
        response.setResponseReason("Invalid Method (kk)");
        return response;
    }
}
