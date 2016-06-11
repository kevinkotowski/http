package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public class HttpHandler implements IHHandler {
    IHMiddleware middleware;
    IHRouter router;

    public HttpHandler( IHMiddleware middleware, IHRouter router) {
        this.middleware = middleware;
        this.router = router;
    }

    public IHResponse handle(IHRequest request) throws Exception {
        IHResponse response;
        response = middleware.transform(request, this.router);
        return response;
    }
}
