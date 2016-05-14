package com.kevinkotowski.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpHandler implements Handler {
    private String docRoot;

    public HttpHandler (String docRoot) {
        this.docRoot = docRoot;
    }

    public IOResponse handle(IORequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setSocket( request.getSocket() );

        if ( request.getMethod().equals("GET") ) {
            String path;
            int ch;

            path = request.getPath();
            if ( path.substring(0, 1).equals("/") ) {
                path = docRoot + path;
            } else {
                path = docRoot + "/" + path;
            }

            System.out.println(request.getMethod() + " " + path);

            StringBuilder stringBuilder = new StringBuilder();
            try {
                response.setResponseCode( request.getResponseCode() );
                response.setResponseReason( request.getResponseReason() );
                FileInputStream fileIn = new FileInputStream(path);

                while((ch = fileIn.read()) != -1){
                    stringBuilder.append((char)ch);
                }
                response.setBody( stringBuilder.toString() );
            } catch (FileNotFoundException e) {
                response.setResponseCode("404");
                response.setResponseReason("File not found (kk)");
            }
        } else {
            response.setResponseCode("405");
            response.setResponseReason("Only GET supported at this time (kk)");
        }
        return response;
    }
}
