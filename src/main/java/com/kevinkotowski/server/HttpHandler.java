package com.kevinkotowski.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpHandler implements Handler {
    public enum FileType {
        TEXT,
        DIRECTORY,
        IMAGE,
        TEAPOT
    }
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
//                Handler handler = this.resolveHandlerType(request);
//                response = handler.run(request, response);
                response = handleGET(request, response);
                break;
            case "OPTIONS":
                response = handleOPTIONS(request, response);
                break;
            case "POST":
                response = handlePOST(request, response);
                break;
            case "PUT":
                response = handlePUT(request, response);
                break;
            default:
                response.setResponseCode("405");
                response.setResponseReason("Only GET supported at this time (kk)");
                break;
        }
        return response;
    }

    private IOResponse handle418(IORequest request, IOResponse response) {
        response.setResponseCode("418");
        response.setResponseReason("I am short and stout");
        response.setBody("<html>\n    <p>I'm a teapot.</p>\n" +
                "    <p>I am short and stout</p>\n</html>");
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
                for (final File subFile : file.listFiles()) {
                    if (subFile.isDirectory()) {
                        response.setBody( "Another directory." );
//                        listFilesForFolder(fileEntry);
                    } else {
                        fileList += this.formatFileLink( subFile.getName() );
//                        System.out.println(subFile.getName());
                    }
                }
                response.setBody( htmlBodyWrapperBefore() + fileList +
                        htmlBodyWrapperAfter() );
            } else {
                FileInputStream fileStream = new FileInputStream(path);

                while((ch = fileStream.read()) != -1){
                    stringBuilder.append((char)ch);
                }

                response.setBody( stringBuilder.toString() );
            }
        } catch (IOException e) {
            if (request.getPath().equals("/redirect")) {
                response.setResponseCode("302");
                response.setResponseReason("Redirect (kk)");
                response.addHeader("Location: http://localhost:5000/");
            } else if (request.getPath().equals("/coffee")) {
                response = this.handle418(request, response);
            } else if ( request.getPath().equals("/tea") ) {
                response.setResponseCode("200");
                response.setResponseReason("Tip me over! (kk)");
            } else {
                response.setResponseCode("404");
                response.setResponseReason("File not found (kk)");
            }
        }
        return response;
    }

    private IOResponse handlePOST(IORequest request, IOResponse response) {
        if (request.getPath().equals("/text-file.txt")) {
            response.setResponseCode("405");
            response.setResponseReason("Method not allowed (kk)");
        } else {
            response.setResponseCode("200");
            response.setResponseReason("OK (kk)");
        }
        return response;
    }

    private IOResponse handlePUT(IORequest request, IOResponse response) {
        if (request.getPath().equals("/file1")) {
            response.setResponseCode("405");
            response.setResponseReason("Method not allowed (kk)");
        } else {
            response.setResponseCode("200");
            response.setResponseReason("OK (kk)");
        }
        return response;
    }

    private IOResponse handleOPTIONS(IORequest request, IOResponse response) {
        response.setResponseCode("200");
        response.setResponseReason("OK (kk)");
        if ( request.getPath().equals("/method_options2") ) {
            response.addHeader("Allow: GET,OPTIONS");
        } else {
            response.addHeader("Allow: GET,HEAD,POST,OPTIONS,PUT");
        }
        return response;
    }

    private String formatFileLink(String fileName) {
        return "\n<a href=\"/" + fileName + "\">" + fileName + "</a><br/>";
    }

    private String htmlBodyWrapperBefore() {
        String html;
        html = "<html>\n    <header></header>\n    <body>\n";
        return html;
    }

    private String htmlBodyWrapperAfter() {
        return "\n    </body>\n</html>";
    }
}
