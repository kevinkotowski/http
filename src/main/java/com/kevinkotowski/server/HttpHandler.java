package com.kevinkotowski.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpHandler implements Handler {
    public IOResponse handle(IORequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setSocket( request.getSocket() );

        if ( request.getMethod().equals("GET") ) {
            int ch;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                response.setResponseCode( request.getResponseCode() );
                response.setResponseReason( request.getResponseReason() );
                FileInputStream fileIn = new FileInputStream(request.getPath());

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
