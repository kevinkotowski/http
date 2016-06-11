package com.kevinkotowski.server;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public interface IHTransformer {
    public IHRequest transformRequest(IHRequest request);
    public IHResponse transformResponse(IHResponse response);
}
