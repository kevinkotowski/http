package com.kevinkotowski.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpControllerSTATIC implements IHController {
    private int rangeMin = -1;
    private int rangeMax = -1;
    private int rangeLast = -1;

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
                    this.setRange(request);
                    if (-1 != this.rangeMin || -1 != this.rangeMax || -1 !=
                            this.rangeLast) {
                        response.setResponseCode("206");
                    }
                    response.setBody(this.getText(fullPath));
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
        String formatted;
        String fileList = new String();
        for (final File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                // there is no req for subdirectory support
            } else {
                fileList += this.formatFileLink(subFile.getName());
            }
        }
        formatted = htmlBodyWrapperBefore() + fileList +
                htmlBodyWrapperAfter();
        return formatted;
    }

    public String getText(String fullPath) throws IOException {
        String body = "";
        int ch;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileStream =
                    new FileInputStream(fullPath);
            int rangeCounter = 0;

            while ((ch = fileStream.read()) != -1) {
                if (this.inRange(rangeCounter)) {
                    stringBuilder.append((char) ch);
                }
                rangeCounter += 1;
            }
            body += this.trimToRange(stringBuilder.toString());
        }
        catch (FileNotFoundException e) {}
        return body;
    }

    private void setRange(IHRequest request) {
        for (String header : request.getHeaders()) {
            if (header.contains("Range")) {
                String[] rangeHeader = header.split(":");
                String[] rangeValue = rangeHeader[1].split("=");
                String[] range = rangeValue[1].split("-");
                if (range[0].length() == 0) {
                    this.rangeLast = Integer.parseInt(range[1]);
                } else {
                    this.rangeMin = range[0].length() == 0 ?
                            -1 : Integer.parseInt(range[0]);
                    this.rangeMax = range.length != 2 ?
                            -1 : Integer.parseInt(range[1]) + 1;
                }
            }
        }
    }

    private boolean inRange(int rangeCounter) {
        boolean inRange = true;
        inRange = rangeCounter >= this.rangeMin;
        if (inRange && this.rangeMax != -1) {
            inRange = rangeCounter < this.rangeMax;
        }
        return inRange;
    }

    private String trimToRange(String body) {
        if (this.rangeLast != -1) {
            body = body.substring( body.length() - (this.rangeLast) );
        }
        return body;
    }

    private String formatFileLink(String fileName) {
        return "\n<a href=\"/" + fileName + "\">" + fileName + "</a><br/>";
    }

    private String htmlBodyWrapperBefore() {
        String html;
        html = "<html>\n    <header></header>\n    <body>\n";
        return html;
    }

    private String htmlBodyWrapperAfter() {
        return "\n    </body>\n</html>";
    }
}
