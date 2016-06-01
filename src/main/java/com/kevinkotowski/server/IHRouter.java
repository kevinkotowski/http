package com.kevinkotowski.server;

import java.io.IOException;
import java.util.List;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public interface IHRouter {
    public String getDocRoot();
    public void registerRoute(IHRoute route);
    public String getOptions(String path);
    public IOResponse route(IORequest request) throws IOException;
}
