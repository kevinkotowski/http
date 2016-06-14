package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class HttpArguments {
    public static String[] parse(String[] args) {
        // returns [0] = port, [1] = working directory
        String[] response = new String[2];
        boolean foundPort = false;
        boolean foundDirectory = false;

        if (args.length == 4) {
            for (int x = 0; x < args.length; x++) {
                if ( (x % 2) == 0 ) {
                    switch (args[x]) {
                        case "-p":
                            response[0] = args[ x+1 ];
                            foundPort = (response[0].length() > 0);
                            if (!foundPort || response[0].startsWith("-")) {
                                throw new IllegalArgumentException(
                                        "Invalid/missing port: " + response[0]);
                            }
                            break;
                        case "-d":
                            response[1] =  args[ x+1 ];
                            foundDirectory = (response[1].length() > 0);
                            if (!foundDirectory ||
                                    response[1].startsWith("-")) {
                                throw new IllegalArgumentException(
                                        "Invalid/missing docroot: " +
                                                response[0]);
                            }
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Invalid option: " +
                                    args[x]);
                    }
                }
            }
        }
        if (!foundPort || !foundDirectory) {
            throw new IllegalArgumentException("Usage: java Http " +
                    "-p <port number> " +
                    "-d <working directory>");
        }
        return response;
    }
}
