package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class http {
    IHServer server;

    public static void main(String[] args) throws IOException {
        String[] parsedArgs = http.handleArguments(args);
        int portNumber = Integer.parseInt(parsedArgs[0]);
        String docRoot = parsedArgs[1];

        IHRouter router = new HttpRouter(docRoot);
        router.registerRoute(new HttpRoute (
                "/",
                HttpMethod.GET, new HttpControllerSTATIC() ));

        IHServer httpServer = new HttpServer(portNumber, router);
        httpServer.listen();
    }

    public static String[] handleArguments(String[] args) {
        // returns [0] = port, [1] = working directory
        String[] response = new String[2];
        boolean foundPort = false;
        boolean foundDirectory = false;

        for (int x = 0; x < args.length; x++) {
            if ( (x % 2) == 0 ) {
                switch (args[x]) {
                    case "-p":
                        response[0] = args[ x+1 ];
                        foundPort = (response[0].length() > 0);
                        if (!foundPort) {
                            throw new RuntimeException(
                                    "Invalid/missing port: " + response[0]);
                        }
                        break;
                    case "-d":
                        response[1] =  args[ x+1 ];
                        foundDirectory = (response[1].length() > 0);
                        if (!foundDirectory) {
                            throw new RuntimeException(
                                    "Invalid/missing port: " + response[0]);
                        }
                        break;
                    default:
                        throw new RuntimeException("Invalid option: " +
                                args[x]);
                }
            }
        }
        if (!foundPort || !foundDirectory) {
            throw new RuntimeException("Usage: java http -p <port number> " +
                    "-d <working directory>");
        }
        return response;
    }

    http( IHServer server ) throws IOException {
        this.server = server;
        server.listen();
    }
}
