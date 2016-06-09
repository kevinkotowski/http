package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class MockRequestParser implements IHRequestParser {
    public IHRequest parse(IOSocket socket) throws IOException {
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("HEAD");
        return request;
    }
}
