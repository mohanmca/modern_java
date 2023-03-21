package com.academy.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

enum Side {
    Buy, Sell
}

public class ProducerConsumer {

    public ProducerConsumer() throws InterruptedException {
        DelayQueue<Order> delayQueue = new DelayQueue<>();
        Thread producerT = new Thread(new Producer(delayQueue));
        Thread consumerT = new Thread(new Consumer(delayQueue));
        producerT.start();
        consumerT.start();
        producerT.join();
        consumerT.join();
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        System.out.println(producerConsumer);
    }


}

//Here the assumption is only one symbol is traded
record Order(int quantity, Side side, double price, long created, long expiryAt) implements Delayed {
    @Override
    public int compareTo(@NotNull Delayed d1) {
        if (!(d1 instanceof Order o)) return -1;
        return Long.compare(this.expiryAt, o.expiryAt);
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        long diff = expiryAt - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.NANOSECONDS);
    }
}

class Consumer implements Runnable {
    private final DelayQueue<Order> queue;
    Random r = new Random();

    public Consumer(DelayQueue<Order> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                System.out.printf("Current queue size %1d \n,", queue.size());
                Order o = queue.poll(5, TimeUnit.SECONDS);
                Order nextOrder = queue.poll(5, TimeUnit.SECONDS);
                if (o != null && nextOrder != null && o.side() != nextOrder.side() && o.price() == nextOrder.price()) {
                    System.out.printf("********Magic auto-order********* matching happened! %1s, %2s, %3d", o, nextOrder, nextOrder.expiryAt() - o.expiryAt());
                } else {
                    System.out.print(" Order o = " + (o != null ? o.toString() : null));
                    System.out.println(", nextOrder o = " + (nextOrder != null ? nextOrder.toString() : null));
                }
            } catch (InterruptedException e) {
                System.out.println("Exception while waiting in consumer Thread - {}" + e.getMessage());
            }
        }
    }
}

class Producer implements Runnable {
    private final DelayQueue<Order> queue;
    Random r = new Random();

    public Producer(DelayQueue<Order> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                System.out.println("Producer polling!");
            } catch (InterruptedException e) {
                System.out.println("Exception while waiting in Producer Thread - {}" + e.getMessage());
            }
            int price = r.nextInt(1, 4);
            long t1 = System.currentTimeMillis();
            long t2 = t1 + (price * 250L);
            Side side = t1 % 2 == 0 ? Side.Buy : Side.Sell;
            queue.offer(new Order(price, side, price, t1, t2));
        }
    }
}