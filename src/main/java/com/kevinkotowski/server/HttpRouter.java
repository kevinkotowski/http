package com.kevinkotowski.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpRouter implements IHRouter {
    String docRoot;
    List<IHRoute> routes = new ArrayList<IHRoute>();

    public HttpRouter(String docRoot) {
        this.docRoot = docRoot;
    }

    public String getDocRoot() {
        return this.docRoot;
    }

    public void registerRoute(IHRoute route) {
        this.routes.add(route);
    }

    public IHController route(IORequest request) {
        IHController controller = null;
        int index = 0;
        int routesLength = this.routes.size();

        while (controller == null && index < routesLength) {
            controller = this.routes.get(index).getController(
                    request.getPath(),
                    request.getMethod() );
            index++;
        }
        return controller;
    }

    public String getOptions(String path) {
        String response = "";

        for (IHRoute route : this.routes) {
            if (route.getPath().equals(path)) {
                response += (response.length() == 0) ? "" : ", ";
            }
            response += route.getMethod().name();
        }
        return response;
    }
}
