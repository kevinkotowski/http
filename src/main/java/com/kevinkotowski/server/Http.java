package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class http {
    IHServer server;

    public static void main(String[] args) throws IOException {
        String[] parsedArgs = HttpArguments.parse(args);
        int portNumber = Integer.parseInt(parsedArgs[0]);
        String docRoot = parsedArgs[1];

        IOServerSocket serverSocket = new HttpServerSocket(portNumber);
        IHNetwork network = new HttpNetwork(serverSocket,
                new HttpRequestParser());
        IOFile logFile = new HttpFile(docRoot + "/logs");
        IHLogger accessLogger = new HttpLogger(logFile);
        IHServer httpServer = new HttpServer( network,
                getMiddleware(),
                getRouter(docRoot, accessLogger) );

        httpServer.listen();
    }

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

    http( IHServer server ) throws IOException {
        this.server = server;
        server.listen();
    }
}
