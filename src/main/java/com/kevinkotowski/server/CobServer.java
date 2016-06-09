package com.kevinkotowski.server;

import java.io.IOException;

/**
 * Created by kevinkotowski on 6/7/16.
 */
public class CobServer extends HttpServer {
    CobServer(int port, String docRoot) throws IOException {
        super(port, docRoot);

        IHRouter router = new HttpRouter(docRoot);

        router.registerRoute( new HttpRoute( "/",
                HttpMethod.GET, new HttpControllerFILE() ) );

        router.registerRoute( new HttpRoute( "/coffee",
                HttpMethod.GET, new HttpControllerTEACUP() ) );

        router.registerRoute( new HttpRoute( "/file1",
                HttpMethod.GET, new HttpControllerFILE() ) );

        router.registerRoute( new HttpRoute( "/form",
                HttpMethod.POST, new HttpControllerUPDATE() ) );

        router.registerRoute( new HttpRoute( "/form",
                HttpMethod.PUT, new HttpControllerUPDATE() ) );

        router.registerRoute( new HttpRoute( "/form",
                HttpMethod.DELETE, new HttpControllerDELETE() ) );

        router.registerRoute( new HttpRoute( "/logs",
                HttpMethod.GET, new CobControllerAUTH() ) );

        router.registerRoute( new HttpRoute( "/method_options",
                HttpMethod.GET, new HttpControllerFILE() ) );

        router.registerRoute( new HttpRoute( "/method_options",
                HttpMethod.PUT, new HttpControllerUPDATE() ) );

        router.registerRoute( new HttpRoute( "/method_options",
                HttpMethod.POST, new HttpControllerUPDATE() ) );

        router.registerRoute( new HttpRoute( "/method_options2",
                HttpMethod.GET, new HttpControllerFILE() ) );

        router.registerRoute( new HttpRoute( "/parameters",
                HttpMethod.GET, new CobControllerPARMS() ) );

        router.registerRoute( new HttpRoute( "/patch-content.txt",
                HttpMethod.PATCH, new HttpControllerIFMATCH() ) );

        router.registerRoute( new HttpRoute( "/redirect",
                HttpMethod.GET, new CobControllerREDIRECT() ) );

        router.registerRoute( new HttpRoute( "/tea",
                HttpMethod.GET, new HttpControllerTEACUP() ) );

        router.registerRoute( new HttpRoute( "/text-file.txt",
                HttpMethod.GET, new HttpControllerFILE() ) );
        
        this.setRouter(router);
    }

}
