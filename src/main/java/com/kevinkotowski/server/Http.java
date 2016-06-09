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

        IHRouter router = new HttpRouter(docRoot);
        router.registerRoute(new HttpRoute (
                "/",
                HttpMethod.GET, new HttpControllerSTATIC() ));

        IHServer httpServer = new HttpServer(portNumber, router);
        httpServer.listen();
    }

    http( IHServer server ) throws IOException {
        this.server = server;
        server.listen();
    }
}
