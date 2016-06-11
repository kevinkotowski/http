package com.kevinkotowski.server;

import java.util.List;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public interface IHTransformer {
    public IHRequest transformRequest(IHRequest request);
    public IHResponse transformResponse(IHResponse response);

//    public IHRequest recurseRequest(
//            IHRequest request, List<IHTransformer> middlewares);
//    public IHResponse recurseResponse(
//            IHResponse response, List<IHTransformer> middlewares);
}
