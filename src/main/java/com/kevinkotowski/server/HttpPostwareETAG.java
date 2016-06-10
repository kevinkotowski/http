package com.kevinkotowski.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class HttpPostwareETAG implements IHPostware {
    public HttpPostwareETAG(String algo) {
        // this implementation is currently not open to extension
        // supporting multiple algos can implemented later when required
        if (!algo.equals("SHA1")) {
            throw new IllegalArgumentException("ERROR: Postware ETag support " +
                    "only for \"SHA1\"");
        }
    }

    public IHResponse transform(IHResponse response) {
        String body = response.getBody();
        String etag;
        if (body != null) {
            if (body.length() > 0) {
                try {
                    etag = this.transformSHA1(body);
                    response.addHeader("ETag: \"" + etag + "\"");
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("ERROR: SHA1 algo not " +
                            "supported");
                }
            }
        }
        return response;
    }

    private String transformSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes();
        md.update(buffer);
        byte[] digest = md.digest();

        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr +=  Integer.toString( ( digest[i] & 0xff ) +
                    0x100, 16).substring( 1 );
        }
        return hexStr;
    }
}
