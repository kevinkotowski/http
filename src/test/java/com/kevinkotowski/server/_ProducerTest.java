package com.kevinkotowski.server;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class _ProducerTest {
    @Test
    public void produce() throws Exception {
        IHNetwork network = new MockNetwork(5000);
        BlockingQueue queue = new LinkedBlockingQueue();

        HttpProducer producer = new HttpProducer(network, queue);
        assertEquals(0, queue.size());
        producer.produce();
        assertEquals(1, queue.size());
        assertTrue(queue.take() instanceof IHRequest);
    }
}