package com.kevinkotowski.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IHRequest {
    public IOSocket getSocket();

    public void setHost(String host);
    public String getHost();

    public void setPath(String path);
    public String getPath();

    public void setDocRoot(String docRoot);
    public String getFullPath();

    public void setAuthorized(boolean authorized);
    public boolean isAuthorized();

    public void setParms(String[][] parms);
    public String[][] getParms();

    public void setMethod(String method);
    public HttpMethod getMethod();

    public void addHeader(String header);
    public List<String> getHeaders();

    public void setContent(String content);
    public boolean hasContent();
    public int getContentLength();
    public String getContent();
}
