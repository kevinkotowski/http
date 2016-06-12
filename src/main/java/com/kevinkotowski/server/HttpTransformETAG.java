package com.kevinkotowski.server;

import java.security.NoSuchAlgorithmException;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class HttpTransformETAG implements IHTransformer {
    public HttpTransformETAG(String algo) {
        // this algo implementation is currently not open to extension
        // supporting multiple algos can implemented later when required
        if (!algo.equals("SHA1")) {
            throw new IllegalArgumentException("ERROR: Postware ETag support " +
                    "only for \"SHA1\"");
        }
    }

    public IHRequest transformRequest(IHRequest request) {
        return request;
    }

    public IHResponse transformResponse(IHResponse response) {
        String body = response.getBody();
        String etag;
        if (body != null) {
            if (body.length() > 0) {
                try {
                    etag = HttpHashAlgoSHA1.hash(body);
                    response.addHeader("ETag: \"" + etag + "\"");
                } catch (NoSuchAlgorithmException e) {
                    System.err.println("ERROR: SHA1 algo not " +
                            "supported");
                }
            }
        }
        return response;
    }
}
