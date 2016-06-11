package com.kevinkotowski.server;

import java.util.List;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public interface IHMiddleware {
    public void registerTransformer(IHTransformer transformer);
    public IHResponse transform(IHRequest request, IHRouter router) throws Exception;

    public IHRequest recurseRequest(
            IHRequest request, List<IHTransformer> middlewares);
    public IHResponse recurseResponse(
            IHResponse response, List<IHTransformer> middlewares);
}
