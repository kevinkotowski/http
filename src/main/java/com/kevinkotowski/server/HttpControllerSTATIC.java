package com.kevinkotowski.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpControllerSTATIC implements IHController {
    private int[] range;

    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());

        String fullPath;

        fullPath = request.getFullPath();
        File file = new File(fullPath);
        IHFileSystem fileSystem = new HttpFileSystem(fullPath);

        try {
            if (fileSystem.exists()) {
                if (fileSystem.isDirectory()) {
                    response.setBody(this.getDirectory(file));
                } else if (fileSystem.isImage()) {
                    response.setImage(fileSystem.getBytes());
                } else {
                    this.range = setRange(request);
                    if (-1 != this.range[0] || -1 != this.range[1] || -1 !=
                            this.range[2]) {
                        response.setResponseCode("206");
                    }
                    response.setBody(this.getText(this.range, fullPath));
                }
            } else {
                response.setResponseCode("404");
                response.setResponseReason("File not found (kk)");
            }
        } catch (IOException e) {
            response.setResponseCode("404");
            response.setResponseReason("File not found (kk)");
        }

        return response;
    }

    public String getDirectory(File file) {
        String fileList = new String();
        for (final File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                // there is no req for subdirectory support
            } else {
                fileList += this.formatFileLink(subFile.getName());
            }
        }
        return fileList;
    }

    public String getText(int[] range, String fullPath) throws IOException {
        String body = "";
        int ch;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileStream =
                    new FileInputStream(fullPath);
            int rangeCounter = 0;

            while ((ch = fileStream.read()) != -1) {
                if (this.inRange(range, rangeCounter)) {
                    stringBuilder.append((char) ch);
                }
                rangeCounter += 1;
            }
            body += this.trimToRange(range[2], stringBuilder.toString());

        }
        catch (FileNotFoundException e) {}
        return body;
    }

    private int[] setRange(IHRequest request) {
        int[] range = { -1, -1, -1 };
        for (String header : request.getHeaders()) {
            if (header.contains("Range")) {
                String[] rangeHeader = header.split(":");
                String[] rangeValue = rangeHeader[1].split("=");
                String[] rangeSplit = rangeValue[1].split("-");
                if (rangeSplit[0].length() == 0) {
                    range[2] = Integer.parseInt(rangeSplit[1]);
                } else {
                    range[0] = rangeSplit[0].length() == 0 ?
                            -1 : Integer.parseInt(rangeSplit[0]);
                    range[1] = rangeSplit.length != 2 ?
                            -1 : Integer.parseInt(rangeSplit[1]) + 1;
                }
            }
        }
        return range;
    }

    public boolean inRange(int[] range, int rangeCounter) {
        boolean inRange = true;
        inRange = rangeCounter >= this.range[0];
        if (inRange && this.range[1] != -1) {
            inRange = rangeCounter < this.range[1];
        }
        return inRange;
    }

    public String trimToRange(int rangeLast, String body) {
        if (rangeLast != -1) {
            body = body.substring( body.length() - (rangeLast) );
        }
        return body;
    }

    public String formatFileLink(String fileName) {
        return "<a href=\"/" + fileName + "\">" + fileName + "</a><br/>\n";
    }
}
