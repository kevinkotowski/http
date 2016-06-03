package com.kevinkotowski.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerPATCH extends HttpControllerPOST {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());

        if (request.hasContent()) {
            response = super.execute(request);
        }
        response.setBody(null);
        response.setResponseCode("204");
        response.setResponseReason("No content (kk)");

        return response;
    }
    private boolean ifMatchEtag(String header) {
        boolean result = false;
        if (header.contains("If-Match")) {
            String[] ifMatchHeader = header.split(":");
            String etag = ifMatchHeader[1].trim();
//            System.out.println("...request.handleIfMatch: " + this.ifMatch);
        }
        // TODO: implement GET to run SHA1 algo for etag check
        return result;
    }
}
