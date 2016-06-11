package com.kevinkotowski.server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _TransformAUTHTest {
    @Test
    public void transformFail() throws Exception {
        String user = "scott";
        String pass = "kevin";
        String authString = user + ":" + pass;
        byte[] bytesEncoded = Base64.getEncoder().encode(authString.getBytes());
        String auth64 = new String(bytesEncoded);

        IHRequest request = new HttpRequest(new MockSocket());
        request.addHeader("Authorization: Basic " + auth64);

        IHMiddleware middleware = new HttpMiddleware();
        List<IHTransformer> transformers = new ArrayList<IHTransformer>();

        IHTransformer transformAUTH = new HttpTransformAUTH(
                "/", "WallyWorld", "scott", "tiger" );
        transformers.add(transformAUTH);

        assertTrue(request.isAuthorized());
        request = middleware.recurseRequest(request, transformers);
        assertFalse(request.isAuthorized());
    }

    @Test
    public void transformSuccess() throws Exception {
        String user = "scott";
        String pass = "tiger";
        String authString = user + ":" + pass;
        byte[] bytesEncoded = Base64.getEncoder().encode(authString.getBytes());
        String auth64 = new String(bytesEncoded);

        IHRequest request = new HttpRequest(new MockSocket());
        request.addHeader("Authorization: Basic " + auth64);

        IHMiddleware middleware = new HttpMiddleware();
        List<IHTransformer> transformers = new ArrayList<IHTransformer>();

        IHTransformer transformAUTH = new HttpTransformAUTH(
                "/", "WallyWorld", "scott", "tiger" );
        transformers.add(transformAUTH);

        assertFalse(request.isAuthorized());
        request = middleware.recurseRequest(request, transformers);
        assertTrue(request.isAuthorized());
    }
}