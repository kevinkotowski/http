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
    private IHNetwork network;
    private IHRouter router;
    private ExecutorService producerPool;
    private ExecutorService consumerPool;

    HttpServer(IHNetwork network, IHRouter router)
            throws IOException {
        this.router = router;
        this.network = network;
    }

    public void listen() throws IOException {
        int poolSize = 4;
        BlockingQueue sharedQueue = new LinkedBlockingQueue();

        //Creating Producer and Consumer thread pools
        this.producerPool = Executors.newFixedThreadPool(poolSize);
        for (int x = 0; x < poolSize; x++) {
            producerPool.submit(new Thread(
                    new HttpProducer(network, sharedQueue)));
        }
        this.consumerPool = Executors.newFixedThreadPool(poolSize);
        for (int z = 0; z < poolSize; z++) {
            consumerPool.submit(new Thread(new HttpConsumer(this.router,
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
        message += " on port " + Integer.toString(this.port);
        message += " for dir " + (this.router.getDocRoot());
        return message;
    }

    private boolean isListening() {
//        boolean listening = false;
//        listening = (this.producerPool != null) && (this.consumerPool != null);
//        if (listening) {
//            listening = ( !this.consumerPool.isTerminated() &&
//                    !this.producerPool.isTerminated());
//        }
//        return listening;
        return (this.network != null);
    }
}
