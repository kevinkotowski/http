package com.kevinkotowski.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpRequest implements IHRequest {
    private HttpMethod method = null;
    private String path = null;
    private List<String> headers = new ArrayList(0);
    private List<String> content = new ArrayList(0);
    private String[][] parms = null;

    private IOSocket socket = null;
    private String docRoot = null;

    private boolean isAuthorized = false;

    public HttpRequest(IOSocket socket) throws IOException {
        this.setSocket(socket);
    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        return this.socket;
    }

    public void setMethod(String method) {
        try {
            this.method = HttpMethod.valueOf(method);
        } catch (RuntimeException e) {
            // null value is handled later, just need to catch exception here
        }
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot;
    }

    public void addHeader(String header) {
        this.headers.add(header);
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public String getFullPath() {
        String fullPath;
        if ( this.path.substring(0, 1).equals("/") ) {
            fullPath = docRoot + path;
        } else {
            fullPath = docRoot + "/" + path;
        }

        int fileIndex = fullPath.indexOf("?");
        fullPath = (fileIndex > 0) ?
                fullPath.substring(0, fileIndex) : fullPath;

        return fullPath;
    }

    public void setParms(String[][] parms) {
        this.parms = parms;
    }

    public String[][] getParms() {
        return this.parms;
    }

    public void addContent(String content) {
        this.content.add(content);
    }

    public int getContentLength() {
        int contentLength = 0;
        for ( String header : this.headers ) {
            if (header.contains("Content-Length")) {
                String[] contentHeader = header.split(":");
                contentLength = Integer.parseInt(contentHeader[1].trim());
            }
        }
        return contentLength;
    }

    public boolean hasContent() {
        return (this.content.size() == 0) ? false : true;
    }

    public String getContent() {
        return this.content.get(0);
    }
}
