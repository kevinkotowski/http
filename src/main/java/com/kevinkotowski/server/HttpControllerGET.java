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
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = HttpResponseFactory.create(request);

        String path;
        int ch;

        path = request.getFullPath();
        File file = new File(path);

        StringBuilder stringBuilder = new StringBuilder();
        try {
            response.setResponseCode(request.getResponseCode());
            response.setResponseReason(request.getResponseReason());
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
                        FileInputStream fileStream = new FileInputStream(path);
                        int rangeCounter = 0;
                        while ((ch = fileStream.read()) != -1) {
                            if (request.inRange(rangeCounter)) {
                                stringBuilder.append((char) ch);
                            }
                            rangeCounter += 1;
                        }
                        body += request.trimToRange(stringBuilder.toString());
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
