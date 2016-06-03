package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IORequest {
//    public void handleRequestLine(String requestLine)
//            throws UnsupportedEncodingException;

    public IOSocket getSocket();
    public void setPath(String path);
    public String getPath();
    public void setParms(String[][] parms);
    public String[][] getParms();
    public void setMethod(String path);
    public HttpMethod getMethod();
    public void addHeader(String header);
    public List<String> getHeaders();
    public void addContent(String content);
    public boolean hasContent();
    public int getContentLength();
    public String getContent();

    public void setDocRoot(String docRoot);
//    public String getDocRoot();
    public String getFullPath();

//    public void setSocket(IOSocket socket);
//    public String getResponseCode();
//    public String getResponseReason();
//    public boolean isAuthorized();
//    public boolean inRange(int rangeCounter);
//    public String trimToRange(String body);
}
