package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerREDIRECT implements IHController {
    public IOResponse execute(IORequest request) {
        IOResponse response = HttpResponseFactory.create(request);
        response.setResponseCode("302");
        response.setResponseReason("Redirect (kk)");
        // TODO: this is cob_spec specific
        response.addHeader("Location: http://localhost:5000/");

        return response;
    }
}
