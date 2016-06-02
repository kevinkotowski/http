package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerAUTH extends HttpControllerGET {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = HttpResponseFactory.create(request);
        if (request.isAuthorized()) {
            response = super.execute(request);
        } else {
            response.setResponseCode("401");
            response.setResponseReason("Unauthorized (kk)");
            // TODO: this is cob_spec specific
            response.addHeader("WWW-Authenticate: Basic realm=\"WallyWorld\"");
        }
        return response;
    }
}
