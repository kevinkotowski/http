package com.kevinkotowski.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class MockRouter implements IHRouter {
    String docRoot;
    List<IHRoute> routes = new ArrayList<IHRoute>();

    public MockRouter(String docRoot) {
        this.docRoot = docRoot;
    }

    public String getDocRoot() {
        return this.docRoot;
    }

    public void registerMiddleware(IHMiddleware middleware) {}
    public void registerRoute(IHRoute route) {
        this.routes.add(route);
    }
    public void registerPostware(IHPostware postware) {}

    public IHResponse route(IHRequest request) throws Exception {
        return new MockResponse();
    }

    public String getOptions(String path) {
        String options = "OPTIONS";
        return options;
    }

    private boolean hasRoute(IHRoute checkRoute) {
        return true;
    }

    private IHController resolveController(IHRequest request) {
        return new MockController();
    }

    private void logAccess(HttpMethod method, String path) {
    }
}

