package MyThreads.ThreadsWithVolatile;

import java.util.Scanner;

public class ThreadsWithVolatile {
    public static void main(String[] args) {

        Work worker1 = new Work();

        Thread thread1 = new Thread(worker1);

        thread1.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        worker1.stopRunning();


        System.out.println("Поток main завершен..");

    }
}

class Work implements Runnable{

    private volatile boolean flagRun = true;

    public void run(){
        String threadName = Thread.currentThread().getName();
        System.out.println("Поток "+threadName+" начал работу..");

        long i = 0L;
        while ((flagRun) && (i<1000000000)) {
            i++;
            System.out.println("Итерация № " + i);
        }

        System.out.println("Поток "+threadName+" завершил работу..");
    }

    public void stopRunning(){
        flagRun = false;
    }

}