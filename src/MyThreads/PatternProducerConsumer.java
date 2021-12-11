package threadsInJava;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class PatternProducerConsumer {

    private static ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);

    public static void main(String[] args){

        Thread thread_Produce = new Thread(new Runnable(){
            public void run(){
                try {
                    produce();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread thread_Consume = new Thread(new Runnable(){
            public void run(){
                try{
                    consume();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

    thread_Produce.start();
    thread_Consume.start();

    try {
        thread_Produce.join();
        thread_Consume.join();
    }catch(InterruptedException e){
        e.printStackTrace();
    }
    }

    public static void produce() throws InterruptedException{
        Random random = new Random();

        while(true){
            list.put(random.nextInt(100));
        }
    }

    public static void consume() throws InterruptedException{
        while(true){
            Thread.sleep(100);

            System.out.println(list.take());
            System.out.println("Количество элементов в списке: "+list.size());
        }
    }


}
