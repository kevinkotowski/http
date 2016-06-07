package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class cob extends http {
    public static void main(String[] args) throws IOException {
        String[] parsedArgs = http.handleArguments(args);
        int portNumber = Integer.parseInt(parsedArgs[0]);
        String docRoot = parsedArgs[1];

        IHServer server = new CobServer(portNumber, docRoot);
        server.listen();
    }

    cob( IHServer server ) throws IOException {
        super(server);
    }
}
