package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpTransformAUTH implements IHTransformer {
    // TODO: change path to static path
    String path;
    String realm;
    String user;
    String pass;
    boolean isAuthorized = false;

    public HttpTransformAUTH(String path, String realm, String user,
                             String pass) {
        this.path = path;
        this.realm = realm;
        this.user = user;
        this.pass = pass;
    }

    public IHRequest transformRequest(IHRequest request) {
        if (this.path.equals(request.getPath())) {
            try {
                this.isAuthorized = checkCredentials(request);
                request.setAuthorized(this.isAuthorized);
            } catch (UnsupportedEncodingException e) {
                System.err.println("ERROR: HttpTransformAUTH no Base64 conversion");
            }
            request.setAuthorized(this.isAuthorized);
        } else {
            this.isAuthorized = true;
        }
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        if (!this.isAuthorized) {
            response.setBody(null);
            response.setResponseCode("401");
            response.setResponseReason("Unauthorized (kk)");
            response.addHeader("WWW-Authenticate: Basic realm=\"" +
                    this.realm + "\"");
        }
        return response;
    }

    private boolean checkCredentials(IHRequest request)
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

                result = ( (auth[0].equals(this.user)) &&
                        (auth[1].equals(this.pass)) );
            }
        }
        return result;
    }
}
