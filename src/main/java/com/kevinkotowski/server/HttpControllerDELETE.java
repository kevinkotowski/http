package com.kevinkotowski.server;

import java.io.File;
import java.io.IOException;

/**
 * Created by kevinkotowski on 6/1/16.
 */
public class HttpControllerDELETE implements IHController {
    public IOResponse execute(IORequest request) throws IOException {
        IOResponse response = HttpResponseFactory.create(request);
        File file = new File( request.getFullPath() );

        if (file.exists()) {
//            System.out.println("...controller.delete deleting file: " + path);
            file.delete();
            response.setResponseCode("200");
            response.setResponseReason("OK (kk)");
        } else {
            response.setResponseCode("404");
            response.setResponseReason("File not found (kk)");
        }

        return response;
    }
}