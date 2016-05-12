package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class MockResponse implements IOResponse {
    public void setSocket(IOSocket socket) throws IOException {}
    public void closeSocket() throws IOException {}
    public void setBody(String body) {}
    public String getBody() {
        return "<mock>body</mock>";
    }
    public void setResponseCode(String code) {}
    public String getResponseCode() {
        return "200";
    }
    public void setResponseReason(String code) {}
    public String getResponseReason() {
        return "mock reason";
    }
    public String getStatusLine() {
        return "HTTP/1.1 200 Mock OK";
    }
    public void run() throws IOException {}
}
