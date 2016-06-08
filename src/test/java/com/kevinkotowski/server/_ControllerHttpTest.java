package com.kevinkotowski.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/2/16.
 */
public class _ControllerHttpTest {
    IOSocket socket = new MockSocket();
    HttpRequest request;

    @Before
    public void setupNewRequest() throws Exception {
        this.request = new HttpRequest(this.socket);
        this.request.setMethod("GET");
        this.request.setPath("/mock");
    }

    @Test
    public void executeDELETE() throws Exception {
        IHController controller = new HttpControllerDELETE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeFILE() throws Exception {
        IHController controller = new HttpControllerFILE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeETAG() throws Exception {
        IHController controller = new HttpControllerIFMATCH();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeUPDATE() throws Exception {
        IHController controller = new HttpControllerUPDATE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }
}