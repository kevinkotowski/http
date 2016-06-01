package com.kevinkotowski.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpRequest implements IORequest {
    private IOSocket socket = null;
    private HttpMethod method = null;
    private String path = null;
    private String docRoot = null;
    private List<String> headers = new ArrayList(0);
    private List<String> content = new ArrayList(0);
    private String responseCode = null;
    private String responseReason = null;
    private String[][] parms = null;
    private int rangeMin = -1;
    private int rangeMax = -1;
    private int rangeLast = -1;
    private boolean isAuthorized = false;
    private int contentLength = 0;
    private String ifMatch = null;

    public HttpRequest() { }

    public HttpRequest(IOSocket socket) throws IOException {
        this.setSocket(socket);

        Scanner scanner;
        InputStream in;
        in = socket.getInputStream();
        scanner = new Scanner(in, "UTF8");

        if (scanner.hasNextLine()) {
            this.handleRequestLine( scanner.nextLine() );
        } else {
            System.out.println("...request.constructor no first line!");
        }

        String headerLine = new String();
        boolean headersDone = false;
        while ( !headersDone ) {
            scanner.hasNextLine();
            headerLine = scanner.nextLine();
            if ( headerLine.length() > 0 ) {
                this.addHeader(headerLine);
            } else {
                headersDone = true;
            }
//            System.out.println("...request.constructor header found: " + headerLine);
        }

//        System.out.println("...request.constructor after scanner");

        this.handleHeaders();

        if (this.getContentLength() > 0) {
            scanner.useDelimiter("");
            String content = "";
            for (int x = 0; x < this.getContentLength(); x++) {
                content += scanner.next();
            }
            this.addContent(content);
        }
//        System.out.println(content);
    }

    public void handleRequestLine(String requestLine)
            throws UnsupportedEncodingException {
        String[] tokens = new String[3];

        tokens = requestLine.split("\\s");
        if (tokens.length != 3) {
            this.responseCode = "400";
            this.responseReason = "Should be 3 request tokens (kk)";
            if (!tokens[2].equals("HTTP/1.1")) {
                this.responseCode = "400";
                this.responseReason = "Only HTTP/1.1 is supported, not: " +
                        tokens[2] + " (kk)";
            }
        } else {
            this.setPath( tokens[1] );
            this.setParms( tokens[1] );
            try {
                this.method = HttpMethod.valueOf( tokens[0] );
                this.responseCode = "200";
                this.responseReason = "OK (kk)";
            } catch(IllegalArgumentException e) {
                System.out.println(tokens[0] + " Invalid Method 405");
                this.responseCode = "405";
                this.responseReason = "Invalid Method (kk)";
            }
        }
    }

    public void handleHeaders() throws UnsupportedEncodingException {
        int headerSize = this.headers.size();

        for (String header: this.headers) {
//            System.out.println("...request.handleHeaders header: " + header);
            this.handleAuthorization(header);
            this.handleRange(header);
            this.handleContent(header);
            this.handleIfMatch(header);
        }
    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        return this.socket;
    }

    public void addHeader(String header) {
        this.headers.add(header);
//        System.out.println("...request.addHeader: " + header);
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot;
    }

    public String getDocRoot() {
        return this.docRoot;
    }

    private void setPath(String path) {
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

    public void setParms(String path) throws UnsupportedEncodingException {
        String queryString = null;
//        path = URLDecoder.decode(path);

        int qsIndex = path.indexOf("?");
        if (qsIndex > 0) {
            queryString = path.substring(qsIndex + 1);
        }

//        System.out.println("...request.handleRequestLine queryString: >" + queryString +"<");
        if (queryString != null) {
            String[] parmPairs = queryString.split("&");
            String[] parm = null;
            String[][] parms = new String[parmPairs.length][2];

            for (int x = 0; x < parmPairs.length; x++) {
                parm = new String[2];
                String pair = parmPairs[x];
                int index = pair.indexOf("=");
                parm[0] = (index > 0) ? URLDecoder.decode(pair.substring(0, index),
                        "UTF-8") : pair;
                parm[1] = (pair.length() > index + 1) && (index > 0) ?
                        URLDecoder.decode(pair.substring(index + 1), "UTF-8") :
                        null;
                parms[x] = parm;
//                System.out.println("...request.handleRequestLine key  : " + parms[x][0]);
//                System.out.println("...request.handleRequestLine value: " + parms[x][1]);
            }
            this.parms = parms;
        }
    }

    public String[][] getParms() {
        return this.parms;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseReason() {
        return this.responseReason;
    }

    public boolean inRange(int rangeCounter) {
        boolean inRange = true;
        inRange = rangeCounter >= this.rangeMin;
        if (inRange && this.rangeMax != -1) {
            inRange = rangeCounter < this.rangeMax;
        }
        return inRange;
    }

    public boolean isAuthorized() {
        return this.isAuthorized;
    }

    public String trimToRange(String body) {
        if (this.rangeLast != -1) {
//            System.out.println("...request.trimToRange before: " + body);
            body = body.substring( body.length() - (this.rangeLast) );
//            System.out.println("...request.trimToRange  after: " + body);
        }
        return body;
    }

    private void handleRange(String header) {
        if (header.contains("Range")) {
//            System.out.println("...request.handleRange header: " + header);
            String[] rangeHeader = header.split(":");
            String[] rangeValue = rangeHeader[1].split("=");
            String[] range = rangeValue[1].split("-");
            if (range[0].length() == 0) {
                this.rangeLast = Integer.parseInt(range[1]);
            } else {
                this.rangeMin = range[0].length() == 0 ? -1 : Integer.parseInt(range[0]);
                this.rangeMax = range.length != 2 ? -1 : Integer.parseInt(range[1]) + 1;
            }
//            System.out.println("...request.handleRange  last: " + this.rangeLast);
//            System.out.println("...request.handleRange start: " + this.rangeMin);
//            System.out.println("...request.handleRange  stop: " + this.rangeMax);
        }
        if (-1 != this.rangeMin  || -1 != this.rangeMax || -1 != this.rangeLast) {
            this.responseCode = "206";
        }
    }

    private void handleAuthorization(String header) throws UnsupportedEncodingException {
        if (header.contains("Authorization")) {
            String[] authHeader = header.split(":");
            int index = authHeader[1].indexOf("Basic");
            String authBasic = authHeader[1].substring(index + 6);
            byte[] decodedBytes = Base64.getDecoder().decode(authBasic);
            String decoded = new String(decodedBytes, "UTF-8");
            String[] auth = decoded.split(":");

//            System.out.println("...request.handleAuthorization decoded user: " + auth[0]);
//            System.out.println("...request.handleAuthorization decoded pass: " + auth[1]);

            this.isAuthorized = ( (auth[0].equals("admin")) && (auth[1].equals("hunter2")) );
        }
    }

    private void handleIfMatch(String header) throws UnsupportedEncodingException {
        if (header.contains("If-Match")) {
            String[] ifMatchHeader = header.split(":");
            this.ifMatch = ifMatchHeader[1].trim();
//            System.out.println("...request.handleIfMatch: " + this.ifMatch);
        }
    }

    public void addContent(String content) {
        this.content.add(content);
//        System.out.println("...request.addContent: " + content);
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public boolean hasContent() {
        return (this.contentLength == 0) ? false : true;
    }

    public String getContent() {
        return this.content.get(0);
    }

    private void handleContent(String header) {
        if (header.contains("Content-Length")) {
            String[] contentHeader = header.split(":");
            this.contentLength = Integer.parseInt(contentHeader[1].trim());
//            System.out.println("...request.handleContent found contentLength: " + Integer.toString(this.contentLength));
        }
    }
}
