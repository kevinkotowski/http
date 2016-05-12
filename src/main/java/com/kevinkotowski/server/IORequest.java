package com.kevinkotowski.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IORequest {
    public void handleRequestLine(String requestLine);
    public String getMethod();
    public String getPath();
    public void addHeader(String header);
//    public String[] getHeaders();
    public void setSocket(IOSocket socket);
    public IOSocket getSocket();
    public String getResponseCode();
    public String getResponseReason();
}
