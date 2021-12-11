package threadsInJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyThreadsPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2); //Создаём пулл из 2-х потоков

        for(int i=0; i<5; i++){
            executorService.submit(new Work(i)); //Добавляем задачи для пулла потоков
        };

        executorService.shutdown(); //Прекращаем прием задач и запускаем потоки

        executorService.awaitTermination(10, TimeUnit.MINUTES); //Ждём завершения всех потоков

    }


}

class Work implements Runnable{
    private int id;

    public Work(int id){
        this.id = id;
    }

    public void run(){
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf("Thread %d was completed work.\n",id);
    }

}