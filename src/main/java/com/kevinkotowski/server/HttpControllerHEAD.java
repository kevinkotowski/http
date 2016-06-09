package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerHEAD extends HttpControllerSTATIC {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = super.execute(request);
        response.setBody(null);
        return response;
    }
}
