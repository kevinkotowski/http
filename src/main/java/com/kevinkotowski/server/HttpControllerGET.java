package com.kevinkotowski.server;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpControllerGET implements IHController {
    private int rangeMin = -1;
    private int rangeMax = -1;
    private int rangeLast = -1;

    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());

        String path;
        int ch;

        path = request.getFullPath();
        File file = new File(path);

        StringBuilder stringBuilder = new StringBuilder();
        try {
            String fileList = new String();

            if (file.isDirectory()) {
                for (final File subFile : file.listFiles()) {
                    if (subFile.isDirectory()) {
                        response.setBody("Another directory.");
                        // TODO? no req for subdirectory support
//                        this.listFilesForFolder(fileEntry);
                    } else {
                        fileList += this.formatFileLink(subFile.getName());
                    }
                }
                response.setBody(htmlBodyWrapperBefore() + fileList +
                        htmlBodyWrapperAfter());
            } else {
                String imageType = this.imageType(file);
                if (imageType != null) {
                    try {
                        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
                        response.setImage(imageBytes, imageType);
                    } catch (IOException e) {
                        System.out.println("...HttpControllerGET " +
                                file.getName() + "File is pretending to be " +
                                "an image: " + file.getName() + "\n");
                    }
                } else {
                    // TODO: This is cob_spec specific
                    String body = "";
                    if (path.contains("parameters")) {
                        String[][] parms = request.getParms();
                        body += this.formatParms(parms);
                    } else {
                        this.setRange(request);
                        FileInputStream fileStream = new FileInputStream(path);
                        int rangeCounter = 0;
                        while ((ch = fileStream.read()) != -1) {
                            if (this.inRange(rangeCounter)) {
                                stringBuilder.append((char) ch);
                            }
                            rangeCounter += 1;
                        }
                        body += this.trimToRange(stringBuilder.toString());
                        if (-1 != this.rangeMin  || -1 != this.rangeMax || -1 != this.rangeLast) {
                            response.setResponseCode("206");
                        }
                    }
                    response.setBody(body);
                }
            }
        } catch (IOException e) {
            response.setResponseCode("404");
            response.setResponseReason("File not found (kk)");
        }
        return response;
    }

    private String imageType(File file) {
        String imageType;

        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        String type = mimeType.split("/")[0].toLowerCase();
        if (mimeType.equals("application/octet-stream") &&
                file.getName().matches(".+\\.png")) {
            imageType = "png";
        } else if (type.equals("image")) {
            imageType = mimeType.split("/")[1].toLowerCase();
        } else {
            imageType = null;
        }
        return imageType;
    }

    private void setRange(IORequest request) {
        for (String header : request.getHeaders()) {
            if (header.contains("Range")) {
//            System.out.println("...GET.setRange header: " + header);
                String[] rangeHeader = header.split(":");
                String[] rangeValue = rangeHeader[1].split("=");
                String[] range = rangeValue[1].split("-");
                if (range[0].length() == 0) {
                    this.rangeLast = Integer.parseInt(range[1]);
                } else {
                    this.rangeMin = range[0].length() == 0 ? -1 : Integer.parseInt(range[0]);
                    this.rangeMax = range.length != 2 ? -1 : Integer.parseInt(range[1]) + 1;
                }
//            System.out.println("...GET.setRange  last: " + this.rangeLast);
//            System.out.println("...GET.setRange start: " + this.rangeMin);
//            System.out.println("...GET.setRange  stop: " + this.rangeMax);
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
//            System.out.println("...request.trimToRange before: " + body);
            body = body.substring( body.length() - (this.rangeLast) );
//            System.out.println("...request.trimToRange  after: " + body);
        }
        return body;
    }

    private String formatParms(String[][] parms) {
        String response = "";
        for (int x = 0; x < parms.length; x++) {
            response += "\n<p>";
            response += parms[x][0];
            if (parms[x][1] != null) {
                response += " = " + parms[x][1];
            }
            response += "</p>";
        }
        return response;
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
