package com.kevinkotowski.server;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _TransformETAGTest {
    @Test
    public void transformSHA1() throws Exception {
        IHResponse response = new HttpResponse(new MockSocket());
        IHTransformer transformETAG = new HttpTransformETAG("SHA1");
        response.setBody("sample body text for SHA1 to convert");

        response = transformETAG.transformResponse(response);
        List<String> headers = response.getHeaders();
        String correctHeader =
                "ETag: \"5af969d05089430848b67d5aa8cd1379fa8ef218\"";
        assertEquals( correctHeader, headers.get(0) );
    }

}