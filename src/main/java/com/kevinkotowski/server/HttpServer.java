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
    private IHNetwork network;
    private IHRouter router;
    private IHHandler handler;
    private ExecutorService producerPool;
    private ExecutorService consumerPool;

    HttpServer(IHNetwork network, IHMiddleware middleware, IHRouter router)
            throws IOException {
        this.network = network;
        this.router = router;
        this.handler = new HttpHandler(middleware, router);
    }

    public void listen() throws IOException {
        int poolSize = 4;
        BlockingQueue sharedQueue = new LinkedBlockingQueue();

        // creating Producer and Consumer thread pools for high concurrency
        // Producers parse Requests from the Network and put on sharedQueue
        this.producerPool = Executors.newFixedThreadPool(poolSize);
        for (int x = 0; x < poolSize; x++) {
            producerPool.submit(new Thread(
                    new HttpProducer(network, sharedQueue)));
        }
        // Consumers pull Requests from sharedQueue and Route to processors
        this.consumerPool = Executors.newFixedThreadPool(poolSize);
        for (int z = 0; z < poolSize; z++) {
            consumerPool.submit(new Thread(new HttpConsumer(this.handler,
                    sharedQueue)));
        }
        System.out.println( this.status() );
    }

    public void close() throws IOException {
        this.consumerPool.shutdown();
        this.producerPool.shutdown();
        this.network = null;
    }

    public String status() {
        String message = this.isListening() ? "Listening" : "Stopped";
        message += " using docroot: " + (this.router.getDocRoot());
        return message;
    }

    private boolean isListening() {
        return (this.network != null);
    }
}
