package com.kevinkotowski.server;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kevinkotowski on 5/19/16.
 */
public class HttpConsumer implements Runnable {
    private IHRouter router;
    private final BlockingQueue sharedQueue;
    String docRoot;

    public HttpConsumer(IHRouter router, BlockingQueue sharedQueue) {
//        public HttpConsumer(String docRoot, BlockingQueue sharedQueue) {
        this.router = router;
//        this.docRoot = docRoot;
        this.sharedQueue = sharedQueue;
    }

    public void run() {
//        System.out.println("...listen new HttpTask");
        IORequest request = null;

        while(true) {
            try {
                request = (IORequest) sharedQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpHandler handler = new HttpHandler(this.router);
            try {
                IOResponse response = handler.handle(request);
                response.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
