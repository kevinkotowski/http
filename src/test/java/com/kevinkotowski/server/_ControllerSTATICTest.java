package com.kevinkotowski.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
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

    @Test
    public void formatFileLink() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String fileName = "filename.txt";
        String fileLink = "<a href=\"/filename.txt\">filename.txt</a><br/>\n";
        assertEquals(fileLink, controller.formatFileLink(fileName));
    }

    @Test
    public void getDirectory() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String fileList =  controller.getDirectory(new MockFile("boo"));
        String fileLink = controller.formatFileLink("filename.txt");
        String threeFiles = fileLink + fileLink + fileLink;
        assertEquals(threeFiles, fileList);
    }

    @Test
    public void getTextAll() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String inputString = "abcdefghijklmnopqrstuvwxyz";
        int[] range = { -1, -1, -1 };
        InputStream input =
                new ByteArrayInputStream( inputString.getBytes(UTF_8) );
        assertEquals(inputString, controller.getText(range, input));
    }

    @Test
    public void getTextBegins() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String inputString = "abcdefghijklmnopqrstuvwxyz";
        int[] range = { 0, 5, -1 };
        InputStream input =
                new ByteArrayInputStream( inputString.getBytes(UTF_8) );
        assertEquals("abcde", controller.getText(range, input));
    }

    @Test
    public void getTextMiddle() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String inputString = "abcdefghijklmnopqrstuvwxyz";
        int[] range = { 6, 11, -1 };
        InputStream input =
                new ByteArrayInputStream( inputString.getBytes(UTF_8) );
        assertEquals("ghijk", controller.getText(range, input));
    }

    @Test
    public void getTextEnd() throws Exception {
        HttpControllerSTATIC controller = new HttpControllerSTATIC();
        String inputString = "abcdefghijklmnopqrstuvwxyz";
        int[] range = { -1, -1, 5 };
        InputStream input =
                new ByteArrayInputStream( inputString.getBytes(UTF_8) );
        assertEquals("vwxyz", controller.getText(range, input));
    }
}
