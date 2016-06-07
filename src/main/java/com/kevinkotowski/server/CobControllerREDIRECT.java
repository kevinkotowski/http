package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class CobControllerREDIRECT implements IHController {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());
        response.setResponseCode("302");
        response.setResponseReason("Redirect (kk)");
        response.addHeader("Location: http://localhost:5000/");

        return response;
    }
}
