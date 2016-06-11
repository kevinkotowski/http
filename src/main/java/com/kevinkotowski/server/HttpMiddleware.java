package com.kevinkotowski.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 6/10/16.
 */
public class HttpMiddleware implements IHMiddleware {
    List<IHTransformer> transformers = new ArrayList<IHTransformer>();

    public void registerTransformer(IHTransformer transformer) {
        this.transformers.add(transformer);
    }

    public IHResponse transform(IHRequest request, IHRouter router)
            throws Exception {
        List<IHTransformer> workingTransformers =
                new ArrayList<IHTransformer>();

        for (IHTransformer transformer : this.transformers) {
            workingTransformers.add(transformer);
        }
        request = this.recurseRequest(request, workingTransformers);

        IHResponse response = router.route(request);

        for (IHTransformer transformer : this.transformers) {
            workingTransformers.add(transformer);
        }
        return this.recurseResponse(response, workingTransformers);
    }

    public IHRequest recurseRequest(
            IHRequest request, List<IHTransformer> transformers) {

        if (transformers.size() > 0) {
            request = transformers.get(0).transformRequest(request);
            transformers.remove(0);
            request = this.recurseRequest(request, transformers);
        }
        return request;
    }

    public IHResponse recurseResponse(
            IHResponse response, List<IHTransformer> transformers) {

        if (transformers.size() > 0) {
            response = transformers.get(0).transformResponse(response);
            transformers.remove(0);
            response = this.recurseResponse(response, transformers);
        }
        return response;
    }
}
