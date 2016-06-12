package com.kevinkotowski.server;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Created by kevinkotowski on 6/12/16.
 */
public class _ControllerSTATICTest {
    @Test
    public void executeSTATIC() throws Exception {
        String docRoot = "/docroot/file";
        IHLogger logger = new MockLogger();

        IHRouter router = http.getRouter(docRoot, logger);

        IOSocket socket = new MockSocket();
        HttpRequest request = new HttpRequest(socket);
        request.setMethod("GET");
        request.setPath("/boo");

        IHResponse response = router.route(request);

        assertEquals("404", response.getResponseCode());
    }


}
