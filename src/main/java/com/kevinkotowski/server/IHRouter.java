package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHRouter {
    public String getDocRoot();
    public void registerMiddleware(IHMiddleware middleware);
    public void registerRoute(IHRoute route);
    public void registerPostware(IHPostware postware);
    public String getOptions(String path);
    public IHRequest processMiddleware(IHRequest request);
    public IHResponse route(IHRequest request) throws Exception;
    public IHResponse processPostware(IHResponse response);
}
