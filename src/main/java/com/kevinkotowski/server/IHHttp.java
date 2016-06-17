package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/14/16.
 */
public interface IHHttp {
    public static IHMiddleware getMiddleware() {
        IHMiddleware middleware = new HttpMiddleware();

        middleware.registerTransformer(new HttpTransformETAG("SHA1") );

        return middleware;
    }

    public static IHRouter getRouter(String docRoot, IHLogger accessLogger) {
        IHRouter router = new HttpRouter(docRoot, accessLogger);

        router.registerRoute(new HttpRoute (
                "/",
                HttpMethod.GET, new HttpControllerSTATIC() ));

        return router;
    }
}
