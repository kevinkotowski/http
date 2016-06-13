package com.kevinkotowski.server;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/12/16.
 */
public class _TransformREDIRECTTest {
    @Test
    public void transformREDIRECT() throws Exception {
        IHTransformer transformREDIRECT = new HttpTransformREDIRECT(
                "/oldpath", "/newpath");

        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath("/oldpath");
        request.setHost("test.com");
        request = transformREDIRECT.transformRequest(request);

        IHResponse response = new HttpResponse(new MockSocket());
        response = transformREDIRECT.transformResponse(response);
        List<String> headers = response.getHeaders();
        String correctHeader =
                "Location: test.com/newpath";

        assertEquals( "302", response.getResponseCode() );
        assertEquals( correctHeader, headers.get(0) );
    }
}