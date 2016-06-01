package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerPUT implements IHController {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = HttpResponseFactory.create(request);
        return response;
    }
}
