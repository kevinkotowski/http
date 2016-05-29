package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHRoute {
    public IHController getController(String path, HttpMethod method);
    public String getPath();
    public HttpMethod getMethod();
}
