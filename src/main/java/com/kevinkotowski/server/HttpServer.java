package com.kevinkotowski.server;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class HttpServer implements Server{
    private int port = 0;
    private String docRoot = null;
    private boolean isListening = false;
    private IONetwork network;

    HttpServer(int port, String docRoot) throws IOException {
        this.port = port;
        this.docRoot = docRoot;
        this.network = new HttpNetwork(port);
    }

    HttpServer(IONetwork network, String docRoot) {
        this.docRoot = docRoot;
        this.network = network;
    }

    public void listen() throws IOException {
//yy        int poolSize = 12;
        this.isListening = true;
        System.out.println( this.status() );

        BlockingQueue sharedQueue = new LinkedBlockingQueue();

        //Creating Producer and Consumer Thread
        Thread producerThread =
                new Thread(new HttpProducer(network, sharedQueue));
        Thread taskThread = new Thread(new HttpTask(docRoot, sharedQueue));

        //Starting producer and Consumer thread
        producerThread.start();
        taskThread.start();
//yy        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
//yy        for (int x = 0; x < poolSize; x++) {
//yy            pool.submit(new Thread(new HttpTask(docRoot, sharedQueue)));
//yy        }

//xx        ExecutorService pool = Executors.newFixedThreadPool(32);

//xx        while ( isListening ) {
//xx            IORequest request = this.network.next();
//xx            pool.submit(new HttpTask(request, docRoot));

//            new Thread(() -> {
//                System.out.println("...listen new thread");
//                HttpHandler handler = new HttpHandler(this.docRoot);
//                IOResponse response = null;
//                try {
//                    response = handler.handle(request);
//                    response.run();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//xx        }
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
}
