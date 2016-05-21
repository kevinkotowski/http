package com.kevinkotowski.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public class HttpRequest implements IORequest {
    private IOSocket socket = null;
    private String method = null;
    private String path = null;
    private String[] headers = null;
    private String responseCode = null;
    private String responseReason = null;
    private String[][] parms = null;

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
            this.responseCode = "200";
            this.responseReason = "OK (kk)";
            this.method = tokens[0];
            this.setPath( tokens[1] );
            this.setParms( tokens[1] );
        }
    }

    public void setSocket(IOSocket socket) {
        this.socket = socket;
    }

    public IOSocket getSocket() {
        return this.socket;
    }

    public void handleOptionalContent(String content) {
        // TODO: this needs to deal with text and binary data
    }

    public String getMethod() {
        return this.method;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setParms(String path) throws UnsupportedEncodingException {
        String queryString = null;
//        path = URLDecoder.decode(path);

        int qsIndex = path.indexOf("?");
        if (qsIndex > 0) {
            queryString = path.substring(qsIndex + 1);
        }

        System.out.println("...request.handleRequestLine queryString: >" + queryString +"<");
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
                System.out.println("...request.handleRequestLine key  : " + parms[x][0]);
                System.out.println("...request.handleRequestLine value: " + parms[x][1]);
            }
            this.parms = parms;
        }
    }

    public String[][] getParms() {
        return this.parms;
    }

    public void addHeader(String header) {
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseReason() {
        return this.responseReason;
    }
}
