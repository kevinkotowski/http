package com.kevinkotowski.server;

import java.io.*;
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
        if (hasRoute(route)) {
            throw new IllegalStateException("Route already exists: " +
                    route.getPath() + " " +
                    route.getMethod()
            );
        } else {
            this.routes.add(route);
        }
    }

    public IOResponse route(IORequest request) throws IOException {
        request.setDocRoot(this.docRoot);

        IHController controller = resolveController(request);
        IOResponse response = controller.execute(request);

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
//        System.out.println("...router.getOptions options: " + options);
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

    private IHController resolveController(IORequest request) {
        HttpMethod method = request.getMethod();
        IHController controller = null;
        int index = 0;
        int routesLength = this.routes.size();

        this.logAccess(method, request.getPath());

        if (method == null) {
            // handle invalid method names
            controller = new HttpController405();
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

        // handle GET requests that don't match explicit routes to ensure 404
        if (controller == null && method == HttpMethod.GET) {
            controller = new HttpControllerGET();
        }

        // all remaining cases are invalid methods
        if (controller == null) {
            controller = new HttpController405();
        }

        return controller;
    }

    private void logAccess(HttpMethod method, String path) {
        String log = method + " " + path + " HTTP/1.1 (kk)\n";
        try {
            File logs = new File(this.docRoot + "/logs");
            if (!logs.exists()) {
                logs.createNewFile();
            }

            if (logs.canWrite()) {
                FileWriter fw = new FileWriter(logs.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(log);
                bw.close();
            }
        } catch (IOException e) {
            System.out.println("Error writing to log access file.");
        }
    }
}