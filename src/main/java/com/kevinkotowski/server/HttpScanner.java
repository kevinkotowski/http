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
        return this.scanner.hasNext();
    }

    public HttpRequest next() throws RuntimeException {
        String command = null;
        String operand = null;

        if (this.scanner.hasNext()) {
            command = this.scanner.next();
        } else throw new RuntimeException("No command found.");

        if (this.scanner.hasNext()) {
            operand = this.scanner.next();
        } else {
            throw new RuntimeException("Missing operand for " + operand);
        }

        return new HttpRequest(command, operand);
    }
}
