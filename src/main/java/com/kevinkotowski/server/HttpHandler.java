package com.kevinkotowski.server;

import java.io.File;
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
        IOResponse response = new HttpResponse();
        response.setSocket( request.getSocket() );

        response.setResponseCode( request.getResponseCode() );
        response.setResponseReason( request.getResponseReason() );

        switch ( request.getMethod() ) {
            case "HEAD":
                response = this.handleGET(request, response);
                response.setBody(null);
                break;
            case "GET":
                response = this.handleGET(request, response);
                break;
            default:
                response.setResponseCode("405");
                response.setResponseReason("Only GET supported at this time (kk)");
                break;
        }
        return response;
    }

    private IOResponse handleGET(IORequest request, IOResponse response) {
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
            File file = new File(path);
            String fileList = new String();

            if (file.isDirectory()) {
                response.setBody( "This is a directory! (kk)" );
                for (final File subFile : file.listFiles()) {
                    if (subFile.isDirectory()) {
                        response.setBody( "Another directory." );
//                        listFilesForFolder(fileEntry);
                    } else {
                        fileList += subFile.getName() + "\n";
//                        System.out.println(subFile.getName());
                    }
                }
                response.setBody(fileList);
            } else {
                FileInputStream fileStream = new FileInputStream(path);

                while((ch = fileStream.read()) != -1){
                    stringBuilder.append((char)ch);
                }

                response.setBody( stringBuilder.toString() );
            }
        } catch (IOException e) {
            response.setResponseCode("404");
            response.setResponseReason("File not found (kk)");
//            e.printStackTrace();
        }
        return response;
    }
}
