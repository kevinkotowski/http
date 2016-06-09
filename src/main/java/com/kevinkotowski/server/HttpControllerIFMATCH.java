package com.kevinkotowski.server;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerIFMATCH extends HttpControllerUPDATE {
    public IOResponse execute(IORequest request) throws Exception {
        IOResponse response = new HttpResponse(request.getSocket());
        boolean isEtagOkay = false;

        for (String header : request.getHeaders()) {
            if (header.contains("If-Match")) {
                try {
                    isEtagOkay = ifMatchEtag(header, request.getFullPath());
                } catch (Exception e) {
                    System.out.println("Patch etag check failed: " + e);
                }
            }
        }

        if (isEtagOkay) {
            if (request.hasContent()) {
                response = super.execute(request);
            }
            response.setBody(null);
            response.setResponseCode("204");
            response.setResponseReason("No Content (kk)");
        } else {
            response.setBody(null);
            response.setResponseCode("412");
            response.setResponseReason("Precondition Failed (kk)");
        }
        return response;
    }

    private boolean ifMatchEtag(String headerIfMatch, String fullPath)
            throws Exception {
        boolean result = false;
        String[] ifMatchHeader = headerIfMatch.split(":");
        String headerEtag = ifMatchHeader[1].trim();
        // TODO: implement GET to run SHA1 algo for etag check
        result = (headerEtag.equals(this.getFileEtag(fullPath)));
        return result;
    }

    private String getFileEtag(String fullPath) throws Exception {
        FileInputStream fileStream = new FileInputStream(fullPath);
        int ch;
        String body = null;
        String etag = null;
        StringBuilder stringBuilder = new StringBuilder();

        while ((ch = fileStream.read()) != -1) {
            stringBuilder.append((char) ch);
        }
        body = stringBuilder.toString();
        etag = this.makeSHA1Hash(body);
        return etag;
    }

    public String makeSHA1Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes();
        md.update(buffer);
        byte[] digest = md.digest();

        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return hexStr;
    }
}
