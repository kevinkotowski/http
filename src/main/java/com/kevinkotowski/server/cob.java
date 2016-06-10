package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class cob extends http {
    public static void main(String[] args) throws IOException {
        String[] parsedArgs = HttpArguments.parse(args);
        int portNumber = Integer.parseInt(parsedArgs[0]);
        String docRoot = parsedArgs[1];
        IOServerSocket serverSocket = new HttpServerSocket(portNumber);
        IHNetwork network = new HttpNetwork(serverSocket,
                new HttpRequestParser());

        IHServer server = new HttpServer(network, getRouter(docRoot));
        server.listen();
    }

    public static IHRouter getRouter(String docRoot) {
        IHRouter router = new HttpRouter(docRoot);
        router.registerRoute(new HttpRoute("/",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/coffee",
                HttpMethod.GET, new HttpControllerTEACUP()));

        router.registerRoute(new HttpRoute("/file1",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.POST, new HttpControllerUPDATE()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.PUT, new HttpControllerUPDATE()));

        router.registerRoute(new HttpRoute("/form",
                HttpMethod.DELETE, new HttpControllerDELETE()));

        router.registerRoute(new HttpRoute("/logs",
                HttpMethod.GET, new CobControllerAUTH()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.PUT, new HttpControllerUPDATE()));

        router.registerRoute(new HttpRoute("/method_options",
                HttpMethod.POST, new HttpControllerUPDATE()));

        router.registerRoute(new HttpRoute("/method_options2",
                HttpMethod.GET, new HttpControllerSTATIC()));

        router.registerRoute(new HttpRoute("/parameters",
                HttpMethod.GET, new CobControllerPARMS()));

        router.registerRoute(new HttpRoute("/patch-content.txt",
                HttpMethod.PATCH, new HttpControllerIFMATCH()));

        router.registerRoute(new HttpRoute("/redirect",
                HttpMethod.GET, new CobControllerREDIRECT()));

        router.registerRoute(new HttpRoute("/tea",
                HttpMethod.GET, new HttpControllerTEACUP()));

        router.registerRoute(new HttpRoute("/text-file.txt",
                HttpMethod.GET, new HttpControllerSTATIC()));

        return router;
    }

    cob( IHServer server ) throws IOException {
        super(server);
    }
}
