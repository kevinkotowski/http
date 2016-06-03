package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpController405 implements IHController {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());

        response.setResponseCode("405");
        response.setResponseReason("Invalid Method (kk)");
        return response;
    }
}
