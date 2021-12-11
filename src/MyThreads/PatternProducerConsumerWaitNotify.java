package threadsInJava;


import org.w3c.dom.ls.LSInput;

import java.util.Scanner;

public class PatternProducerConsumerWaitNotify {
    public static void main(String[] args) {

        WaitAndNotify wn = new WaitAndNotify();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}

class WaitAndNotify{

    public void producer() throws InterruptedException{
        System.out.println("Начали исполнять Продюсера..");

        synchronized (this) {
            wait(); //wait for this
        }
        System.out.println("Закончили исполнять Продюсера");
    }

    public void consumer() throws InterruptedException{
        Thread.sleep(500);
        System.out.println("Начали исполнять Потребителя..");

        System.out.println("Ждем нажатия клавиши..");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        synchronized (this) {
            notify();
        }
        Thread.sleep(2000);
        System.out.println("Закончили исполнять Потребителя");

    }

}