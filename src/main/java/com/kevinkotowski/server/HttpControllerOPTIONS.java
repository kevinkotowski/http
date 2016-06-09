package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerOPTIONS implements IHController {
    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());
        response.setBody("OK");
        return response;
    }
}
