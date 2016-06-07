package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class CobControllerAUTH extends HttpControllerGET {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());
        if (this.isAuthorized(request)) {
            response = super.execute(request);
        } else {
            response.setResponseCode("401");
            response.setResponseReason("Unauthorized (kk)");
            response.addHeader("WWW-Authenticate: Basic realm=\"WallyWorld\"");
        }
        return response;
    }

    private boolean isAuthorized(IORequest request)
            throws UnsupportedEncodingException {
        boolean result = false;
        for (String header : request.getHeaders()) {
            if (header.contains("Authorization")) {
                String[] authHeader = header.split(":");
                int index = authHeader[1].indexOf("Basic");
                String authBasic = authHeader[1].substring(index + 6);
                byte[] decodedBytes = Base64.getDecoder().decode(authBasic);
                String decoded = new String(decodedBytes, "UTF-8");
                String[] auth = decoded.split(":");

//            System.out.println("...request.handleAuthorization decoded user: " + auth[0]);
//            System.out.println("...request.handleAuthorization decoded pass: " + auth[1]);

                result = ( (auth[0].equals("admin")) && (auth[1].equals("hunter2")) );
            }
        }
        return result;
    }
}
