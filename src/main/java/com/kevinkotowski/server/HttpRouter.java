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
        HttpMethod method = request.getMethod();
        request.setDocRoot(this.docRoot);

        IHController controller = null;
        int index = 0;
        int routesLength = this.routes.size();

        this.logAccess(method, request.getPath());

        while (controller == null && index < routesLength) {
            controller = this.routes.get(index).getController(
                    request.getPath(), method);
            index++;
        }

        IOResponse response = null;
        if (controller != null) {
            response = controller.execute(request);
        } else {
            HttpHandler handler = new HttpHandler(this);
            response = handler.handle(request);
        }

        if (method == HttpMethod.OPTIONS) {
            response = HttpResponseFactory.create(request);
            response.setBody( "OK" );
            response.addHeader("Allow: " +
                    this.getOptions( request.getPath() ));
        }
        return response;
    }

    public String getOptions(String path) {
        String options = "OPTIONS";

        for (IHRoute route : this.routes) {
            if (route.getPath().equals(path)) {
                options += "," + route.getMethod().name();
            }
        }
        System.out.println("...router.getOptions options: " + options);
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

    private void logAccess(HttpMethod method, String path)
            throws IOException {
        String log = method + " " + path + " HTTP/1.1 (kk)";
        File logs = new File(this.docRoot + "/logs");
        if (!logs.exists()) {
//            System.out.println("...handler.persistFile creating new file");
            logs.createNewFile();
        }

        if (logs.canWrite()) {
            FileWriter fw = new FileWriter(logs.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(log);
            bw.close();
        }
    }
}
