package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerUPSERT implements IHController {
    public IHResponse execute(IHRequest request) throws Exception {
        IHResponse response = new HttpResponse(request.getSocket());
        try {
            if (request.hasContent()) {
                this.persistFile(request.getFullPath(), request.getContent());
                response.setResponseCode("200");
                response.setResponseReason("OK (kk)");
            } else {
                response.setResponseCode("500");
                response.setResponseReason("Internal Server Error (kk)");
            }
        } catch (IOException e) {
            response.setResponseCode("500");
            response.setResponseReason("Internal Server Error (kk)");
        }

        return response;
    }

    private void persistFile(String fullPath, String content)
            throws IOException {
        File file = new File(fullPath);

        if (!file.exists()) {
            file.createNewFile();
        }

        if (file.canWrite()) {
            FileInputStream fileStream =
                    new FileInputStream(file.getAbsoluteFile());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } else {
            System.err.println("ERROR: Can't write to file! " + fullPath);
        }
    }
}
