package com.kevinkotowski.server;

import java.util.List;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHRouter {
    public String getDocRoot();
    public void registerRoute(IHRoute route);
    public String getOptions(String path);
    public IHController route(IORequest request);
}
