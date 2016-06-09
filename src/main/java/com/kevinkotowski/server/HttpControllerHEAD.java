package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerHEAD extends HttpControllerSTATIC {
    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = super.execute(request);
        response.setBody(null);
        return response;
    }
}
