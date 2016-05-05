package com.kevinkotowski.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpRequest {
    private String command = null;
    private String operand = null;

    public HttpRequest(String command, String operand) {
        this.command = command;
        this.operand = operand;
    }

    public void execute(OutputStream out) throws IOException {
        this.logOperation(out);
//        if ( this.command == "GET" ) {
            this.executeGET(out, this.operand);
//        } else {
//            throw new RuntimeException("HTPP command other than GET used.");
//        }
    }

    public String getCommand() {
        return this.command;
    }

    public String getOperand() {
        return this.operand;
    }

    public void executeGET(OutputStream out, String filePath)
            throws IOException {
        FileInputStream fileIn = new FileInputStream(filePath);

        while (fileIn.available() > 0) {
            out.write(fileIn.read());
        }
    }

    private void logOperation(OutputStream out) throws IOException {
        out.write(this.command.getBytes(UTF_8));
        out.write(" ".getBytes(UTF_8));
        out.write(this.operand.getBytes(UTF_8));
        out.write("\n".getBytes(UTF_8));
    }
}
