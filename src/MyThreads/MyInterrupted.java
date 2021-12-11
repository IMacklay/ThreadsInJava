package threadsInJava;

import java.util.Random;

public class MyInterrupted {

    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 1_000_000_000; i++) {

                    //if(Thread.currentThread().isInterrupted()) System.out.println("1");

                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        System.out.println("This thread was interrupted..");
                        return;
                    }

                    System.out.println(Math.sin(random.nextDouble()));

                }
            }
        });

        thread.start();

        //thread.join();
        Thread.sleep(1000);
        thread.interrupt(); // Посылаем сообщение завершении потоку
    }
}
