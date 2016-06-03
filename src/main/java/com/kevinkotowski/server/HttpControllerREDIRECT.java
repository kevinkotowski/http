package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerREDIRECT implements IHController {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());
        response.setResponseCode("302");
        response.setResponseReason("Redirect (kk)");
        // TODO: this is cob_spec specific
        response.addHeader("Location: http://localhost:5000/");

        return response;
    }
}
