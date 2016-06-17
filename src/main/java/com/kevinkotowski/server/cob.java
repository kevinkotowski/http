package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class Cob implements IHHttp {
    private IHServer server;

    public static void main(String[] args) throws IOException {
        String[] parsedArgs = HttpArguments.parse(args);
        int portNumber = Integer.parseInt(parsedArgs[0]);
        String docRoot = parsedArgs[1];
        IOServerSocket serverSocket = new HttpServerSocket(portNumber);
        IHNetwork network = new HttpNetwork(serverSocket,
                new HttpRequestParser());
        IOFile logFile = new HttpFile(docRoot + "/logs");
        IHLogger accessLogger = new HttpLogger(logFile);

        IHServer server = new HttpServer( network,
                getMiddleware(),
                getRouter(docRoot, accessLogger) );

        server.listen();
    }

    public static IHRouter getRouter(String docRoot, IHLogger accessLogger) {
        IHRouter router = new HttpRouter(docRoot, accessLogger);
        router.registerRoute(new HttpRoute("/",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/coffee",
                HttpMethod.GET, new HttpControllerTEACUP()));

        router.registerRoute(new HttpRoute("/file1",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.POST, new HttpControllerUPSERT()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.PUT, new HttpControllerUPSERT()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.DELETE, new HttpControllerDELETE()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.PUT, new HttpControllerUPSERT()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.POST, new HttpControllerUPSERT()));

        router.registerRoute(new HttpRoute("/method_options2",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/parameters",
                HttpMethod.GET, new CobControllerPARMS()));

        router.registerRoute(new HttpRoute("/patch-content.txt",
                HttpMethod.PATCH, new HttpControllerIFMATCH()));

        router.registerRoute(new HttpRoute("/tea",
                HttpMethod.GET, new HttpControllerTEACUP()));

        router.registerRoute(new HttpRoute("/text-file.txt",
                HttpMethod.GET, new HttpControllerSTATIC()));

        return router;
    }

    public static IHMiddleware getMiddleware() {
        IHMiddleware middleware = new HttpMiddleware();

        middleware.registerTransformer(new HttpTransformREDIRECT (
                "/redirect", "/"));

        middleware.registerTransformer(new HttpTransformAUTH (
                "/logs", "WallyWorld", "admin", "hunter2") );

        middleware.registerTransformer(new HttpTransformETAG(
                "SHA1") );

        return middleware;
    }

    Cob(IHServer server ) throws IOException {
        this.server = server;
        server.listen();
    }
}
