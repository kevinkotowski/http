package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHRouter {
    public String getDocRoot();
//    public void registerMiddleware(IHTransformer middleware);
//    public IHRequest transformRequest(IHRequest request);
//    public IHResponse transformResponse(IHResponse response);
    public void registerRoute(IHRoute route);
    public String getOptions(String path);
    public IHResponse route(IHRequest request) throws Exception;
}
