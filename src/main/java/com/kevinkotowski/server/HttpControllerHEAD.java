package com.kevinkotowski.server;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerHEAD extends HttpControllerSTATIC {
    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());

        String fullPath = request.getFullPath();
        IHFileSystem fileSystem = new HttpFileSystem(fullPath);

        if (!fileSystem.exists()) {
            response.setResponseCode("404");
            response.setResponseReason("File not found (kk)");
        }

        return response;
    }
}
