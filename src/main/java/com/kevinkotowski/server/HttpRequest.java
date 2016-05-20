package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpRequest implements IORequest {
    private IOSocket socket = null;
    private String method = null;
    private String path = null;
    private String[] headers = null;
    private String responseCode = null;
    private String responseReason = null;

    public void handleRequestLine(String requestLine) {
        String[] tokens = new String[3];

        tokens = requestLine.split("\\s");
        if (tokens.length != 3) {
            this.responseCode = "400";
            this.responseReason = "Should be 3 request tokens (kk)";
            if (!tokens[2].equals("HTTP/1.1")) {
                this.responseCode = "400";
                this.responseReason = "Only HTTP/1.1 is supported, not: " +
                        tokens[2] + " (kk)";
            }
        } else {
            this.responseCode = "200";
            this.responseReason = "OK (kk)";
            this.method = tokens[0];
            this.setPath( tokens[1] );
        }
    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        return this.socket;
    }

    public void handleOptionalContent(String content) {
        // TODO: this needs to deal with text and binary data
    }

    public String getMethod() {
        return this.method;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void addHeader(String header) {
//        this.headers.a
    }

//    public String[] getHeaders() {
//        return this.headers;
//    }
//
//    public void setResponseCode(String code) {
//        if (code.length() > 3) {
//            throw new RuntimeException("ERROR: Response code too long: " + code);
//        }
//        this.responseCode = code;
//    }

    public String getResponseCode() {
        return this.responseCode;
    }

//    public void setResponseReason(String reason) {
//        this.responseReason = reason;
//    }

    public String getResponseReason() {
        return this.responseReason;
    }
}
