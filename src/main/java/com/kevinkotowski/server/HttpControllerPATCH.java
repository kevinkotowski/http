package com.kevinkotowski.server;

import java.io.File;
import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerPATCH extends HttpControllerPOST {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = HttpResponseFactory.create(request);

        if (request.hasContent()) {
            response = super.execute(request);
        }
        response.setBody(null);
        response.setResponseCode("204");
        response.setResponseReason("No content (kk)");

        return response;
    }
}
