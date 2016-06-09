package com.kevinkotowski.server;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class HttpResponse implements IHResponse {
    private IOSocket socket;
    private String responseCode;
    private String responseReason;
    private List<String> headers = new ArrayList();
    private String body;
    private byte[] image;
    private String imageType;
    private boolean isImage;
    private PrintStream out;

    public HttpResponse(IOSocket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintStream(socket.getOutputStream(), true);
        this.setResponseCode("200");
        this.setResponseReason("OK (kk)");
    }

    public void run() throws Exception {
        if (!this.socket.isClosed()) {
            this.writeln( this.getStatusLine() );

            List<String> headers = this.getHeaders();
            int length = headers.size();
            for (int x = 0; x < length; x++) {
                this.writeln( headers.get(x) );
            }
            this.writeln( "" );

            String body = this.getBody();
            if (body != null) {
                this.write( body );
            } else {
                if (this.isImage) {
                    this.writeImage();
                }
            }
            this.closeSocket();
        } else {
            throw new IllegalStateException("Error: Socket is closed.");
        }
    }

    public void closeSocket() throws IOException {
        this.out.flush();
        this.out.close();
        this.socket.close();
    }

    public void setResponseCode(String code) {
        if (code.length() > 3) {
            throw new RuntimeException("ERROR: Response code too long: " +
                    code);
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

    public void addHeader(String header) {
        this.headers.add(header);
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setImage(byte[] imageBytes) {
        this.image = imageBytes;
        this.isImage = true;
    }

    public void writeImage() throws IOException {
        this.out.write(this.image);
    }

    private void writeln(String string) throws IOException {
        byte[] bytes = (string + "\n").getBytes("UTF8");
        this.out.write(bytes);
        this.out.flush();
    }

    private void write(String string) throws IOException {
        byte[] bytes = (string).getBytes("UTF8");
        this.out.write(bytes);
        this.out.flush();
    }
}
