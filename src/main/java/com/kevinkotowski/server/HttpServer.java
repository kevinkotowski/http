package com.kevinkotowski.server;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements IHServer{
    private int port = 0;
    private String docRoot = null;
    private boolean isListening = false;
    private IONetwork network;
    private IHRouter router;

    HttpServer(int port, String docRoot) throws IOException {
        this.port = port;
        this.docRoot = docRoot;
        this.network = new HttpNetwork(port);
        this.router = new HttpRouter(this.docRoot);
        this.setRoutes();
    }

//    HttpServer(IONetwork network, String docRoot) {
//        this.docRoot = docRoot;
//        this.network = network;
//    }

    public void listen() throws IOException {
        int poolSize = 4;
        this.isListening = true;
        System.out.println( this.status() );

        BlockingQueue sharedQueue = new LinkedBlockingQueue();

        //Creating Producer and Consumer thread pools
        ExecutorService producerPool = Executors.newFixedThreadPool(poolSize);
        for (int x = 0; x < poolSize; x++) {
            producerPool.submit(new Thread(
                    new HttpProducer(network, sharedQueue)));
        }
        ExecutorService consumerPool = Executors.newFixedThreadPool(poolSize);
        for (int z = 0; z < poolSize; z++) {
            consumerPool.submit(new Thread(new HttpConsumer(this.router,
                    sharedQueue)));
        }
    }

    public void close() throws IOException {
        this.isListening = false;
        this.network = null;
    }

    public String status() {
        String message = this.isListening ? "Listening" : "Waiting";
        message += " on port " + Integer.toString(this.port);
        message += " for dir " + (this.docRoot);
        return message;
    }

    public void setRoutes() {
        this.router.registerRoute(new HttpRoute(
                "/",
                HttpMethod.GET, new HttpControllerGET() ) );

        this.router.registerRoute(new HttpRoute(
                "/coffee",
                HttpMethod.GET, new HttpController418() ) );

        this.router.registerRoute(new HttpRoute(
                "/file1",
                HttpMethod.GET, new HttpControllerGET() ) );

        this.router.registerRoute(new HttpRoute(
                "/form",
                HttpMethod.POST, new HttpControllerPOST() ) );

        this.router.registerRoute(new HttpRoute(
                "/form",
                HttpMethod.PUT, new HttpControllerPUT() ) );

        this.router.registerRoute(new HttpRoute(
                "/form",
                HttpMethod.DELETE, new HttpControllerDELETE() ) );

        this.router.registerRoute(new HttpRoute(
                "/logs",
                HttpMethod.GET, new HttpControllerAUTH() ) );

        this.router.registerRoute(new HttpRoute(
                "/method_options",
                HttpMethod.GET, new HttpControllerGET() ) );
        this.router.registerRoute(new HttpRoute(
                "/method_options",
                HttpMethod.PUT, new HttpControllerPUT() ) );
        this.router.registerRoute(new HttpRoute(
                "/method_options",
                HttpMethod.POST, new HttpControllerPOST() ) );

        this.router.registerRoute(new HttpRoute(
                "/method_options2",
                HttpMethod.GET, new HttpControllerGET() ) );

        this.router.registerRoute(new HttpRoute(
                "/patch-content.txt",
                HttpMethod.PATCH, new HttpControllerPATCH() ) );

        this.router.registerRoute(new HttpRoute(
                "/redirect",
                HttpMethod.GET, new HttpControllerREDIRECT() ) );

        this.router.registerRoute(new HttpRoute(
                "/tea",
                HttpMethod.GET, new HttpController418() ) );

        this.router.registerRoute(new HttpRoute(
                "/text-file.txt",
                HttpMethod.GET, new HttpControllerGET() ) );
    }
}
