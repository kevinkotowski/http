package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpResponseFactory {
    static IOResponse create(IORequest request) {
        IOResponse response = new HttpResponse();
        try {
            response.setSocket( request.getSocket() );
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setResponseCode( request.getResponseCode() );
        response.setResponseReason( request.getResponseReason() );
        return response;
    }
}
