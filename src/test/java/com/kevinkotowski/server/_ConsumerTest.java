package com.kevinkotowski.server;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _ConsumerTest {
    @Test
    public void consume() throws Exception {
        IHRouter router = new MockRouter("/docroot");
        BlockingQueue queue = new LinkedBlockingQueue();
        IOSocket socket = new MockSocket();
        queue.put(new HttpRequest(socket));

        HttpConsumer consumer = new HttpConsumer(new HttpHandler(
                new HttpMiddleware(), router ), queue);

        assertEquals(1, queue.size());
        consumer.consume();
        assertEquals(0, queue.size());
    }
}