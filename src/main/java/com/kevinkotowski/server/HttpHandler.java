package com.kevinkotowski.server;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpHandler implements IOHandler {
    public IOResponse handle(IORequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        if ( request.getMethod().equals("GET") ) {
            int ch;
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fileIn = new FileInputStream(request.getPath());

            while((ch = fileIn.read()) != -1){
                stringBuilder.append((char)ch);
            }
            response.setBody( stringBuilder.toString() );
        }
        return response;
    }
}
