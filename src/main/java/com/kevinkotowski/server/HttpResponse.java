package com.kevinkotowski.server;

import java.io.UnsupportedEncodingException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpResponse implements IOResponse {
    private String responseCode;
    private String responseReason;
    private String[] headers;
    private String body;

    public void setResponseCode(String code) {
        if (code.length() > 3) {
            throw new RuntimeException("ERROR: Response code too long: " + code);
        }
        this.responseCode = code;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseReason(String reason) {
        this.responseReason = reason;
    }

    public String getResponseReason() {
        return this.responseReason;
    }

//    public String get() throws UnsupportedEncodingException {
////        return (this.header + this.body).getBytes("UTF8");
//        return this.header + "\n" + this.body;
//    }

//    public void setHeader(String header) {
//        this.header = header;
//    }
//
//    public String getHeader() {
//        return this.header;
//    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }
}
