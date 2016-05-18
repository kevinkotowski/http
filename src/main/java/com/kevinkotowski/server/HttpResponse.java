package com.kevinkotowski.server;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    private StringArray headers = new StringArray();
    private String body;
//    private BufferedImage image;
    private byte[] image;
    private String imageType;
    private boolean isImage;
    private PrintStream out;

    public HttpResponse() {
        this.addHeader("Host: localhost:5000");
    }

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

    public void addHeader(String header) {
        this.headers.add(header);
    }

    public StringArray getHeaders() {
        return this.headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void run() throws IOException {
        this.writeln( this.getStatusLine() );

        StringArray headers = this.getHeaders();
        int length = headers.getSize();
        for (int x = 0; x < length; x++) {
            this.writeln( headers.get(x) );
        }
        this.writeln( "" );

        String body = this.getBody();
        if (body != null) {
            this.writeln( body );
        } else {
            if (this.isImage) {
                this.writeImage();
            }
        }

        this.closeSocket();
    }

//    public void setImage(BufferedImage buffImage, String imageType) {
    public void setImage(byte[] imageBytes, String imageType) {
        this.image = imageBytes;
        this.imageType = imageType;
//        this.addHeader("Content-Type: image/" + this.imageType);
        this.isImage = true;
    }

    public void writeImage() throws IOException {
        this.out.write(this.image);
//        ImageIO.write(this.image, this.imageType, this.out);
    }

    private void writeln(String string) throws IOException {
        byte[] bytes = (string + "\n").getBytes("UTF8");
        this.out.write(bytes);
        this.out.flush();
    }
}
