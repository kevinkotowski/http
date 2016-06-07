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
    public void executeGET() throws Exception {
        IHController controller = new HttpControllerFILE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeHEAD() throws Exception {
        IHController controller = new HttpControllerHEAD();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeINVALID() throws Exception {
        IHController controller = new HttpControllerINVALID();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeOPTIONS() throws Exception {
        IHController controller = new HttpControllerOPTIONS();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePATCH() throws Exception {
        IHController controller = new HttpControllerETAG();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePOST() throws Exception {
        IHController controller = new HttpControllerUPDATE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePUT() throws Exception {
        IHController controller = new HttpControllerUPDATE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeTEACUP() throws Exception {
        IHController controller = new HttpControllerTEACUP();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }
}