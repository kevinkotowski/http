package com.kevinkotowski.server;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kevinkotowski on 5/20/16.
 */
public class HttpProducer implements Runnable {
    private final BlockingQueue sharedQueue;
    IHNetwork network;

    public HttpProducer(IHNetwork network, BlockingQueue sharedQueue) {
        this.network = network;
        this.sharedQueue = sharedQueue;
    }

    public void run() {
        while (true) {
            this.produce();
        }
    }

    public void produce() {
        IHRequest request = null;
        try {
            request = this.network.next();
            if (request != null) {
                this.sharedQueue.put(request);
            } else {
                System.out.println("...producer no request!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
