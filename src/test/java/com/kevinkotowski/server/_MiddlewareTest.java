package com.kevinkotowski.server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _MiddlewareTest {
    @Test
    public void recursiveRequestTransformation() throws Exception {
        IHRequest request = new HttpRequest(new MockSocket());
        String originalPath = "/original/path";
        String origText = "Original text.";
        String newText = "New Text!";
        request.setPath(originalPath);
        request.setContent(origText);
        assertEquals(origText, request.getContent());

        IHTransformer transform1 = new MockTransform(originalPath, "Kevin");
        IHTransformer transform2 = new MockTransform(originalPath, "FAIL");
        IHTransformer transform3 = new MockTransform(originalPath, newText);

        List<IHTransformer> transformers = new ArrayList<IHTransformer>();

        IHMiddleware middleware = new HttpMiddleware();

        transformers.add(transform1);
        transformers.add(transform2);
        transformers.add(transform3);

        request = middleware.recurseRequest(request, transformers);
        assertEquals(newText, request.getContent());
    }

    @Test
    public void recursiveResponseTransformation() throws Exception {
        String path = "/test/path";
        String origText = "Original text.";
        String newText = "New Text!";

        // a request is needed as MockTransform matches the path of the request
        IHRequest request = new HttpRequest(new MockSocket());
        request.setPath(path);

        IHResponse response = new HttpResponse(new MockSocket());
        response.setBody(origText);

        IHTransformer transform1 = new MockTransform(path, "Kevin");
        IHTransformer transform2 = new MockTransform(path, "FAIL");
        IHTransformer transform3 = new MockTransform(path, newText);

        List<IHTransformer> transformers = new ArrayList<IHTransformer>();

        IHMiddleware middleware = new HttpMiddleware();

        // request path is matched and saved in MockTransform
        transformers.add(transform1);
        transformers.add(transform2);
        transformers.add(transform3);
        request = middleware.recurseRequest(request, transformers);

        // reference list needs to be rebuilt as the recursion knocks it down
        transformers.add(transform1);
        transformers.add(transform2);
        transformers.add(transform3);

        assertEquals(origText, response.getBody());
        response = middleware.recurseResponse(response, transformers);
        assertEquals(newText, response.getBody());
    }
}