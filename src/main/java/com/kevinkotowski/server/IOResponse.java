package com.kevinkotowski.server;

import java.io.UnsupportedEncodingException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public interface IOResponse {
//    public void setHeader(String header);
//    public String getHeader();
    public void setBody(String body);
    public String getBody();
    public void setResponseCode(String code);
    public String getResponseCode();
    public void setResponseReason(String code);
    public String getResponseReason();
}