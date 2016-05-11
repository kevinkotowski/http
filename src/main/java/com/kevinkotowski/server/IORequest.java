package com.kevinkotowski.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IORequest {
    public void setMethod(String method);
    public String getMethod();
    public void setPath(String request);
    public String getPath();
    public void addHeader(String header);
    public String[] getHeaders();
    public void setSocket(Socket socket);
    public Socket getSocket();
    public void setResponseCode(String code);
    public String getResponseCode();
    public void setResponseReason(String code);
    public String getResponseReason();
}
