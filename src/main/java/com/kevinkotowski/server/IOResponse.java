package com.kevinkotowski.server;

import java.io.IOException;
import java.util.List;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IOResponse {
    public void addHeader(String header);
    public List<String> getHeaders();
    public void setSocket(IOSocket socket) throws IOException;
    public void closeSocket() throws IOException;
    public void setBody(String body);
    public String getBody();
    public void setImage(byte[] imageBytes, String imageType);
    public void writeImage() throws IOException;
    public void setResponseCode(String code);
    public String getResponseCode();
    public void setResponseReason(String code);
    public String getResponseReason();
    public String getStatusLine();
    public void run() throws IOException;
}

