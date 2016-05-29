package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpControllerGET implements IHController {
    IORequest request;

    public HttpControllerGET(IORequest request) {
        this.request = request;
    }

    public IOResponse execute() {
        return new HttpResponse();
    }
}
