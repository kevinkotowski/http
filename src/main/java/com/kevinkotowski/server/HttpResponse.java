package com.kevinkotowski.server;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpResponse implements IOResponse {
    private IOSocket socket;
    private String responseCode;
    private String responseReason;
    private String body;
    private PrintStream out;

    public void setSocket(IOSocket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintStream(socket.getOutputStream(), true);
    }

    public void closeSocket() throws IOException {
        this.out.flush();
        this.out.close();
        this.socket.close();
    }

    public void setResponseCode(String code) {
        if (code.length() > 3) {
            throw new RuntimeException("ERROR: Response code too long: " + code);
        }
        this.responseCode = code;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseReason(String reason) {
        this.responseReason = reason;
    }

    public String getResponseReason() {
        return this.responseReason;
    }

    public String getStatusLine() {
        return "HTTP/1.1 " + this.responseCode + " " + this.responseReason;
    }

    public String getHeaders() {
        return "Host: localhost:5000\n";
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void run() throws IOException {
        this.writeln( this.getStatusLine() );
        this.writeln( this.getHeaders() );
        this.writeln( this.getBody() );
        this.closeSocket();
    }

    private void writeln(String string) throws IOException {
        byte[] bytes = (string + "\n").getBytes("UTF8");
        this.out.write(bytes);
        this.out.flush();

    }
}
