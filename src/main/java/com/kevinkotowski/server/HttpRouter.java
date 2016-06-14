package com.kevinkotowski.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkotowski on 5/28/16.
 */
public class HttpRouter implements IHRouter {
    String docRoot;
    List<IHRoute> routes = new ArrayList<IHRoute>();
    IHLogger accessLogger;

    public HttpRouter(String docRoot, IHLogger accessLogger) {
        this.docRoot = docRoot;
        this.accessLogger = accessLogger;
    }

    public String getDocRoot() {
        return this.docRoot;
    }

    public void registerRoute(IHRoute route) {
        if (hasRoute(route)) {
            throw new IllegalStateException("Route already exists: " +
                    route.getPath() + " " +
                    route.getMethod()
            );
        } else {
            this.routes.add(route);
        }
    }

    public IHResponse route(IHRequest request) throws Exception {
        request.setDocRoot(this.docRoot);

        IHController controller = resolveController(request);
        IHResponse response = controller.execute(request);

        // this is an appropriate header for all errors and methods
        response.addHeader("Allow: " +
                this.getOptions( request.getPath() ));

        return response;
    }

    public String getOptions(String path) {
        String options = "OPTIONS";

        for (IHRoute route : this.routes) {
            HttpMethod method = route.getMethod();
            if (route.getPath().equals(path)) {
                options += "," + method.name();
                if (method == HttpMethod.GET) {
                    options += ",HEAD";
                }
            }
        }
        return options;
    }

    private boolean hasRoute(IHRoute checkRoute) {
        boolean response = false;
        for (IHRoute route : this.routes) {
            if ( checkRoute.getPath().equals( route.getPath() ) &&
                    checkRoute.getMethod() == route.getMethod() ) {
                response = true;
                break;
            }
        }
        return response;
    }

    private IHController resolveController(IHRequest request) {
        HttpMethod method = request.getMethod();
        IHController controller = null;
        int index = 0;
        int routesLength = this.routes.size();

        this.logAccess(method, request.getPath());

        if (method == null) {
            // handle invalid method names
            controller = new HttpControllerINVALID();
        } else {
            // handle methods that aren't explicitly routed
            switch (method) {
                case OPTIONS:
                    controller = new HttpControllerOPTIONS();
                    break;
                case HEAD:
                    controller = new HttpControllerHEAD();
                    break;
            }
        }

        // handle methods with explicit routing defined
        while (controller == null && index < routesLength) {
            controller = this.routes.get(index).getController(
                    request.getPath(), method);
            index++;
        }

        // handle GET requests that don't match explicit routes
        // NOTE: This could just assign 404, but this way a "default" Http
        //       server actually serves directories, files, and images via GET
        if (controller == null && method == HttpMethod.GET) {
            controller = new HttpControllerSTATIC();
        }

        // all remaining cases are invalid methods
        if (controller == null) {
            controller = new HttpControllerINVALID();
        }

        return controller;
    }

    private void logAccess(HttpMethod method, String path) {
        String log = method + " " + path + " HTTP/1.1 (kk)\n";
        this.accessLogger.writeln(log);
    }
}
