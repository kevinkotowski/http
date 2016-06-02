package com.kevinkotowski.server;

import javax.net.SocketFactory;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/11/16.
 */
public class MockRequest implements IORequest{
    private IOSocket socket = null;
    private HttpMethod method = null;
    private String path = null;
    private String docRoot = null;
    private String[] headers = null;
    private String responseCode = null;
    private String responseReason = null;

    public MockRequest(IOSocket socket) {

    }

    public void handleRequestLine(String requestLine) {
        String[] tokens = new String[3];

        tokens = requestLine.split("\\s");
        this.setPath( tokens[1] );

        try {
            this.method = HttpMethod.valueOf( tokens[0] );
            this.responseCode = "200";
            this.responseReason = "Mock OK (kk)";
        } catch(IllegalArgumentException e) {
            System.out.println("Illegal method: " + tokens[0]);
            this.responseCode = "405";
            this.responseReason = "Mock Invalid Method (kk)";
        }

    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        MockSocket socket = new MockSocket();
        return socket;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot;
    }

    public String getDocRoot() {
        return this.docRoot;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public String getFullPath() {
        return this.docRoot + "/" + this.path;
    }

    public void addHeader(String header) {
//        this.headers.a
    }

    public int getContentLength() {
        return 0;
    }

    public void addContent(String content) { }

    public boolean hasContent() { return false; }

    public String getContent() { return null; }

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

    public String[][] getParms() { return null; };

    public boolean isAuthorized() { return true; };

    public boolean inRange(int rangeCounter) { return true; };

    public String trimToRange(String body) { return body; };
}

