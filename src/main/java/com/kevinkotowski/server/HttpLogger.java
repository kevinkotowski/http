package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class HttpLogger implements IHLogger {
    IOFile log;

    public HttpLogger(IOFile log) {
        this.log = log;
    }

    public void writeln(String message) {
        try {
            if (!this.log.exists()) {
                this.log.createNewFile();
            }

            if (this.log.canWrite()) {
                this.log.writeln(message);
            }
        } catch (IOException e) {
            System.err.println("ERROR: Writing log file: " +
                    this.log.getAbsoluteFile());
        }
    }
}
