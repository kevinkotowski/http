package com.kevinkotowski.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kevinkotowski on 6/2/16.
 */
public class _ControllerCobTest {
    IOSocket socket = new MockSocket();
    IORequest request;

    @Before
    public void setupNewRequest() throws Exception {
        this.request = new HttpRequest(this.socket);
        this.request.setMethod("GET");
        this.request.setPath("/mock");
    }

    @Test
    public void executeAUTH() throws Exception {
        this.request.addHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");
        IHController controller = new CobControllerAUTH();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeREDIRECT() throws Exception {
        IHController controller = new CobControllerREDIRECT();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }
}
