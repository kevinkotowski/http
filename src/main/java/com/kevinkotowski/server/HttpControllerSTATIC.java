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
public class HttpControllerSTATIC implements IHController {
    private int rangeMin = -1;
    private int rangeMax = -1;
    private int rangeLast = -1;

    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = new HttpResponse(request.getSocket());

        String path;
        int ch;

//      FileSystem(String path)
//        boolean isDirectory()
//        boolean isImage()
//        boolean isTextFile()
//        byte[] getBytes


        path = request.getFullPath();
        File file = new File(path);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String fileList = new String();

            if (file.isDirectory()) {
                for (final File subFile : file.listFiles()) {
                    if (subFile.isDirectory()) {
                        response.setBody("Another directory.");
                        // there is no req for subdirectory support
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
                        System.out.println("Error: HttpControllerSTATIC " +
                                file.getName() + "File is pretending to be " +
                                "an image: " + file.getName() + "\n");
                    }
                } else {
                    String body = "";
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
                String[] rangeHeader = header.split(":");
                String[] rangeValue = rangeHeader[1].split("=");
                String[] range = rangeValue[1].split("-");
                if (range[0].length() == 0) {
                    this.rangeLast = Integer.parseInt(range[1]);
                } else {
                    this.rangeMin = range[0].length() == 0 ? -1 : Integer.parseInt(range[0]);
                    this.rangeMax = range.length != 2 ? -1 : Integer.parseInt(range[1]) + 1;
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
