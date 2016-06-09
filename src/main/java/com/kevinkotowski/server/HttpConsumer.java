package com.kevinkotowski.server;

import java.util.concurrent.BlockingQueue;

/**
 * Created by kevinkotowski on 5/19/16.
 */
public class HttpConsumer implements Runnable {
    private IHRouter router;
    private final BlockingQueue sharedQueue;

    public HttpConsumer(IHRouter router, BlockingQueue sharedQueue) {
        this.router = router;
        this.sharedQueue = sharedQueue;
    }

    public void run() {
        while(true) {
            this.consume();
        }
    }

    public void consume() {
        IHRequest request = null;
        IHResponse response = null;

        try {
            request = (IHRequest) sharedQueue.take();
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
