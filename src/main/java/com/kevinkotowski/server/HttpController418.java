package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/31/16.
 */
public class HttpController418 implements IHController {
    public IOResponse execute(IORequest request) {
        IOResponse response = HttpResponseFactory.create(request);

        response.setResponseCode("418");
        response.setResponseReason("I'm a teapot");
        response.setBody("<html>\n    <p>I'm a teapot.</p>\n" +
                "    <p>I am short and stout</p>\n</html>");
        return response;
    }
}
