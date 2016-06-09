package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpControllerTEACUP implements IHController {
    public IHResponse execute(IHRequest request) throws IOException {
        IHResponse response = new HttpResponse(request.getSocket());

        switch (request.getPath()) {
            case "/tea":
                response.setResponseCode("200");
                response.setResponseReason("Tip me over! (kk)");
                break;
            default:
                response.setResponseCode("418");
                response.setResponseReason("I'm a teapot");
                response.setBody("<html>\n    <p>I'm a teapot.</p>\n" +
                        "    <p>I am short and stout</p>\n</html>");
                break;
        }
        return response;
    }
}
