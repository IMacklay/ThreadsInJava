package threadsInJava;

import java.util.LinkedList;
import java.util.Queue;

public class PatternProducerConsumerII {
    public static void main(String[] args) {

        ProducerConsumer pc = new ProducerConsumer();

        Thread thread_Producer = new Thread(new Runnable() {
            @Override
            public void run() {
                pc.produce();
            }
        });


        Thread thread_Consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                pc.consume();
            }
        });

        thread_Producer.start();
        thread_Consumer.start();

        try {
            thread_Producer.join();
            thread_Consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class ProducerConsumer {
    private final int COUNT_LIMIT = 10;
    private final Object lock = new Object();
    private Queue<Integer> queue = new LinkedList<>();

    int i = 0;

    public void produce() {
        while (true) {

            synchronized (lock) {

                while (queue.size() == COUNT_LIMIT) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                queue.offer(i++);

                lock.notify();

            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consume() {


        while (true) {

            synchronized (lock) {

                while (queue.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Take el with value is "+queue.poll());
                System.out.println("Queue size is "+queue.size());

                lock.notify();

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}