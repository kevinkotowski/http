package com.kevinkotowski.server;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kevinkotowski on 5/20/16.
 */
public class HttpProducer implements Runnable {
    private final BlockingQueue sharedQueue;
    IONetwork network;

    public HttpProducer(IONetwork network, BlockingQueue sharedQueue) {
        this.network = network;
        this.sharedQueue = sharedQueue;
    }

    public void run() {
        while (true) {
            IORequest request = null;
            try {
                request = this.network.next();
                this.sharedQueue.put(request);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
