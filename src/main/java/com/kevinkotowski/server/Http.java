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

        IHServer httpServer = new HttpServer(network, getRouter(docRoot));
        httpServer.listen();
    }

    public static IHRouter getRouter(String docRoot) {
        IHRouter router = new HttpRouter(docRoot);
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
