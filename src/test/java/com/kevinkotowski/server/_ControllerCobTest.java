package com.kevinkotowski.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 6/2/16.
 */
public class _ControllerCobTest {
    MockSocket socket = new MockSocket();
    MockRequest request;

    @Before
    public void setupNewRequest() throws Exception {
        this.request = new MockRequest(this.socket);
        this.request.handleRequestLine("GET /mock HTTP/1.1");
    }

    @Test
    public void execute418() throws Exception {
        IHController controller = new HttpController418();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeAUTH() throws Exception {
        IHController controller = new HttpControllerAUTH();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeREDIRECT() throws Exception {
        IHController controller = new HttpControllerREDIRECT();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }
}
