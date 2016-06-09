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
    private IHNetwork network;
    private IHRouter router;

    HttpServer(int port, IHRouter router) throws IOException {
        this.port = port;
        this.docRoot = router.getDocRoot();
        this.network = new HttpNetwork(port);
        this.router = router;
    }

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
        message += " for dir " + (this.router.getDocRoot());
        return message;
    }
}
