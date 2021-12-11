package myLearning.threadsInJava;

import java.util.*;

public class MySynchronizedBlock {
    public static void main(String[] args) {

        new ArrayMethods().Main();

    }
}

class ArrayMethods{

    Random random = new Random();

    Object lock1 = new Object();
    Object lock2 = new Object();


    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new LinkedList<>();

    public void fillList1(){
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void fillList2(){
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }


    public void work(){
        for(int i=0; i<500; i++){
            fillList1();
            fillList2();

        }
    }

    public void Main(){
        Long timeStart = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable(){
            public void run(){
                work();
            }
        });

        Thread thread2 = new Thread(new Runnable(){
            public void run(){
                 work();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //work();

        Long timeEnd = System.currentTimeMillis();

        System.out.println("list1: "+list1.size()+" list2: "+list2.size()+" Time(ms): "+(timeEnd-timeStart));
    }

}