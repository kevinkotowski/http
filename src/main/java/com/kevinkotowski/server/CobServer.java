package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class CobServer extends HttpServer {
    CobServer(int port, String docRoot) throws IOException {
        super(port, docRoot);
    }

    public void setRoutes() {
        this.registerRoute( "/",
                HttpMethod.GET, new HttpControllerGET() );

        this.registerRoute( "/coffee",
                HttpMethod.GET, new HttpController418() );

        this.registerRoute( "/file1",
                HttpMethod.GET, new HttpControllerGET() );

        this.registerRoute( "/form",
                HttpMethod.POST, new HttpControllerPOST() );

        this.registerRoute( "/form",
                HttpMethod.PUT, new HttpControllerPUT() );

        this.registerRoute( "/form",
                HttpMethod.DELETE, new HttpControllerDELETE() );

        this.registerRoute( "/logs",
                HttpMethod.GET, new CobControllerAUTH() );

        this.registerRoute( "/method_options",
                HttpMethod.GET, new HttpControllerGET() );

        this.registerRoute( "/method_options",
                HttpMethod.PUT, new HttpControllerPUT() );

        this.registerRoute( "/method_options",
                HttpMethod.POST, new HttpControllerPOST() );

        this.registerRoute( "/method_options2",
                HttpMethod.GET, new HttpControllerGET() );

        this.registerRoute( "/parameters",
                HttpMethod.GET, new CobControllerPARMS() );

        this.registerRoute( "/patch-content.txt",
                HttpMethod.PATCH, new HttpControllerPATCH() );

        this.registerRoute( "/redirect",
                HttpMethod.GET, new CobControllerREDIRECT() );

        this.registerRoute( "/tea",
                HttpMethod.GET, new HttpController418() );

        this.registerRoute( "/text-file.txt",
                HttpMethod.GET, new HttpControllerGET() );
    }

}
