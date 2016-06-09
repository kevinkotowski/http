package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * Created by kevinkotowski on 6/2/16.
 */
public class HttpRequestParser implements IHRequestParser {
    public IHRequest parse(IOSocket socket) throws IOException {
        Scanner scanner;
        InputStream in;
        in = socket.getInputStream();
        scanner = new Scanner(in, "UTF8");
        IHRequest request = new HttpRequest(socket);

        request = this.parseRequestLine( request, scanner );
        request = this.parseHeaders( request, scanner );
        request = this.parseContent( request, scanner );

        return request;
    }

    private IHRequest parseRequestLine(IHRequest request, Scanner scanner)
            throws UnsupportedEncodingException {
        String requestLine = scanner.nextLine();
        String[] tokens = new String[3];

        if (scanner.hasNextLine()) {
            tokens = requestLine.split("\\s");
            if (tokens.length != 3) {
                System.out.println("Invalid request: " +
                        requestLine);
            } else {
                request.setMethod( tokens[0] );
                request.setPath( this.parsePath( tokens[1] ) );
                request.setParms( this.parseQuery( tokens[1] ) );
            }
        } else {
            System.out.println("Invalid Request: No first line");
        }
        return request;
    }

    private String parsePath( String pathWithQuery ) {
        int queryIndex = pathWithQuery.indexOf("?");
        return (queryIndex > 0) ?
                pathWithQuery.substring(0, queryIndex) : pathWithQuery;
    }

    private String[][] parseQuery( String pathWithQuery)
            throws UnsupportedEncodingException {
        String[][] queryParms = null;

        int queryIndex = pathWithQuery.indexOf("?");
        String queryString = (queryIndex > 0) ?
                pathWithQuery.substring(queryIndex + 1) : null;

        if (queryString != null) {
            String[] parmPairs = queryString.split("&");
            String[] parm = null;
            String[][] parms = new String[parmPairs.length][2];

            for (int x = 0; x < parmPairs.length; x++) {
                parm = new String[2];
                String pair = parmPairs[x];
                int index = pair.indexOf("=");
                parm[0] = (index > 0) ?
                        URLDecoder.decode(pair.substring(0, index),
                        "UTF-8") : pair;
                parm[1] = (pair.length() > index + 1) && (index > 0) ?
                        URLDecoder.decode(pair.substring(index + 1), "UTF-8") :
                        null;
                parms[x] = parm;
            }
            queryParms = parms;
        }
        return queryParms;
    }

    private IHRequest parseHeaders(IHRequest request, Scanner scanner) {
        String headerLine;
        boolean headersDone = false;
        while ( !headersDone ) {
            scanner.hasNextLine();
            headerLine = scanner.nextLine();
            if ( headerLine.length() > 0 ) {
                request.addHeader(headerLine);
            } else {
                headersDone = true;
            }
        }
        return request;
    }

    private IHRequest parseContent(IHRequest request, Scanner scanner) {
        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            scanner.useDelimiter("");
            String content = "";
            for (int x = 0; x < contentLength; x++) {
                content += scanner.next();
            }
            request.setContent(content);
        }
        return request;
    }
}