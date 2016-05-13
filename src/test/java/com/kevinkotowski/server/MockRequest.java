package com.kevinkotowski.server;

import javax.net.SocketFactory;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/11/16.
 */
public class MockRequest implements IORequest{
    private IOSocket socket = null;
    private String method = null;
    private String path = null;
    private String[] headers = null;
    private String responseCode = null;
    private String responseReason = null;

    public void handleRequestLine(String requestLine) {
        String[] tokens = new String[3];

        tokens = requestLine.split("\\s");
        this.method = tokens[0];
        this.setPath( tokens[1] );

        this.responseCode = "200";
        this.responseReason = "Mock OK";
    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        MockSocket socket = new MockSocket();
        return socket;
    }

    public void handleOptionalContent(String content) {
        // TODO: this needs to deal with text and binary data
    }

//    public void setMethod(String method) {
//        this.method = method;
//    }

    public String getMethod() {
        return this.method;
    }

    public void setPath(String path) {
        if ( path.substring(0, 1).equals("/") ) {
            path = "." + path;
        }
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void addHeader(String header) {
//        this.headers.a
    }

    public String[] getHeaders() {
        return this.headers;
    }

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
}

