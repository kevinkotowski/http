package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHController {
    public IOResponse execute(IORequest request) throws IOException;

    default IOResponse setupResponse(IORequest request) throws IOException {
        IOResponse response = new HttpResponse();
        response.setSocket( request.getSocket() );

        response.setResponseCode( request.getResponseCode() );
        response.setResponseReason( request.getResponseReason() );

        return response;
    }
}
