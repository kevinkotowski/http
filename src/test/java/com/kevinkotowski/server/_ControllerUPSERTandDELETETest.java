package com.kevinkotowski.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevinkotowski on 6/12/16.
 */
public class _ControllerUPSERTandDELETETest {
    String workingDir = System.getProperty("user.dir");
    String testDir = "/src/test/java/com/kevinkotowski/server";

    @Test
    public void executeUPSERTfailure() throws Exception {
        HttpRequest request = new HttpRequest(new MockSocket());
        request.setMethod("GET");
        request.setPath("/boo");

        HttpControllerUPSERT controller = new HttpControllerUPSERT();
        IHResponse response = controller.execute(request);

        assertEquals("500", response.getResponseCode());
    }

    @Test
    public void executeUPSERTfailureWithContent() throws Exception {
        // NOTE: This tests a different path in the transformer than w/o content
        HttpRequest request = new HttpRequest(new MockSocket());
        request.setMethod("GET");
        request.setPath("/boo");
        request.setContent("Kevin was here.");

        HttpControllerUPSERT controller = new HttpControllerUPSERT();
        IHResponse response = controller.execute(request);

        assertEquals("500", response.getResponseCode());
    }

    @Test
    public void executeDELETEfailure() throws Exception {
        HttpRequest request = new HttpRequest(new MockSocket());
        request.setDocRoot(this.workingDir + this.testDir);
        request.setPath("/boo");

        HttpControllerDELETE controller = new HttpControllerDELETE();
        IHResponse response = controller.execute(request);

        assertEquals("404", response.getResponseCode());
    }

    @Test
    public void upsertFileAndDelete() throws Exception {
        HttpControllerUPSERT controllerUPSERT = new HttpControllerUPSERT();
        String fileName = "/TestUpsertDelete.txt";

        IHRequest request = new HttpRequest(new MockSocket());
        request.setDocRoot(this.workingDir + this.testDir);
        request.setPath(fileName);
        request.setContent("Kevin was here.");

        IHResponse response = controllerUPSERT.execute(request);
        assertEquals("200", response.getResponseCode());

        HttpControllerDELETE controllerDELETE = new HttpControllerDELETE();

        response = controllerDELETE.execute(request);
        assertEquals("200", response.getResponseCode());
    }
}
