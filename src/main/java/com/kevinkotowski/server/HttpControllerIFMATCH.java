package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerIFMATCH extends HttpControllerUPDATE {
    public IHResponse execute(IHRequest request) throws Exception {
        IHResponse response = new HttpResponse(request.getSocket());
        boolean isEtagOkay = false;

        for (String header : request.getHeaders()) {
            if (header.contains("If-Match")) {
                try {
                    isEtagOkay = ifMatchEtag(header, request.getFullPath());
                } catch (Exception e) {
                    System.err.println("ERROR: Etag check failed: " + e);
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
        etag = HttpHashAlgoSHA1.hash(body);
        return etag;
    }
}
