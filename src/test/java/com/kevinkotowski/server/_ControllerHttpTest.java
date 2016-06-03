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
    public void execute404() throws Exception {
        IHController controller = new HttpController404();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void execute405() throws Exception {
        IHController controller = new HttpController405();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeDELETE() throws Exception {
        IHController controller = new HttpControllerDELETE();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeGET() throws Exception {
        IHController controller = new HttpControllerGET();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeHEAD() throws Exception {
        IHController controller = new HttpControllerHEAD();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executeOPTIONS() throws Exception {
        IHController controller = new HttpControllerOPTIONS();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePATCH() throws Exception {
        IHController controller = new HttpControllerPATCH();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePOST() throws Exception {
        IHController controller = new HttpControllerPOST();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }

    @Test
    public void executePUT() throws Exception {
        IHController controller = new HttpControllerPUT();
        assertTrue(controller.execute(this.request) instanceof IOResponse);
    }
}