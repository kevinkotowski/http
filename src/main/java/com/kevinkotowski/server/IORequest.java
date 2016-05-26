package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IORequest {
    public void handleRequestLine(String requestLine) throws UnsupportedEncodingException;
    public String getMethod();
    public String getPath();
    public void addHeader(String header);
    public void addContent(String content);
    public int getContentLength();
    public boolean hasContent();
    public String getContent();
    public void setSocket(IOSocket socket);
    public IOSocket getSocket();
    public String getResponseCode();
    public String getResponseReason();
    public String[][] getParms();
    public boolean isAuthorized();
    public boolean inRange(int rangeCounter);
    public String trimToRange(String body);
}
