package com.kevinkotowski.server;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpScanner implements StreamScanner {
    Scanner scanner;

    public HttpScanner(InputStream in) {
        this.scanner = new Scanner(in, "UTF8");
    }

    public boolean hasNext() {
        return this.scanner.hasNextLine();
    }

    public HttpRequest next() throws RuntimeException {
        HttpRequest request = new HttpRequest();
        String headerLine = new String();

        // TODO: get initial line
        request.handleRequestLine( this.scanner.nextLine() );

        // TODO: get header lines
        while ( (headerLine = this.scanner.nextLine()).length() > 0 ) {
            request.addHeader(headerLine);
        }

        // TODO: get optional content
//        if ( this.scanner.hasNextLine() ) {
//            response.handleOptionalContent( this.scanner.nextLine() );
//        }
        // if any additional data, give to response.handleOptionalData

        return request;
    }
}
