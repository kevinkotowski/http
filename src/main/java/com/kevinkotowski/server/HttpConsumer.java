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
        this.router = router;
        this.sharedQueue = sharedQueue;
    }

    public void run() {
        IORequest request = null;
        IOResponse response = null;

        while(true) {
            try {
                request = (IORequest) sharedQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                response = router.route(request);
                response.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
