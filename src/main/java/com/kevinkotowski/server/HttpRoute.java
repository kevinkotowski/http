package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpRoute implements IHRoute {
    String path;
    HttpMethod method;
    IHController controller;

    public HttpRoute(String path, HttpMethod method, IHController controller) {
        this.path = path;
        this.method = method;
        this.controller = controller;
    }

    public String getPath() {
        return this.path;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public IHController getController(String path, HttpMethod method) {
        IHController response = null;
        if (this.path.equals(path) && this.method == method) {
            response = this.controller;
        }
        return response;
    }
}
