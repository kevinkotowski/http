package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerPOST implements IHController {
    public IOResponse execute(IORequest request) throws Exception {
        IOResponse response = new HttpResponse(request.getSocket());
        if (request.hasContent()) {
            this.persistFile(request.getFullPath(), request.getContent());
        }
        response.setResponseCode("200");
        response.setResponseReason("OK (kk)");

        return response;
    }

    private void persistFile(String fullPath, String content) throws IOException {
        File file = new File(fullPath);

        if (!file.exists()) {
            file.createNewFile();
        }

        if (file.canWrite()) {
            FileInputStream fileStream = new FileInputStream(file.getAbsoluteFile());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } else {
            System.out.println("ERROR: Can't write to file! " + fullPath);
        }
    }
}
