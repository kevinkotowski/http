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
    public void setPath(String path);
    public String getPath();
    public void setParms(String[][] parms);
    public String[][] getParms();
    public void setMethod(String path);
    public HttpMethod getMethod();
    public void addHeader(String header);
    public List<String> getHeaders();
    public void setContent(String content);
    public boolean hasContent();
    public int getContentLength();
    public String getContent();

    public void setDocRoot(String docRoot);
    public String getFullPath();
}
