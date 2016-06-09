package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kevinkotowski on 5/12/16.
 */
public class HttpSocket implements IOSocket {
    private Socket socket;

    public HttpSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }
}
