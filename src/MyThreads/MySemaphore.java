package threadsInJava;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MySemaphore {
    public static void main(String[] args) throws InterruptedException{
        Connection connection = Connection.getConnection();


        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for(int i=0; i<1000; i++){
            executorService.submit(()->connection.work());
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);

        connection.showTotalCountQuery();
    }
}

//Singleton
class Connection{

    private static Connection connection = new Connection();
    private int queryCount;
    private int totalCountQuery;
    Semaphore semaphore = new Semaphore(10);
    private Random random = new Random();



    private Connection(){

    }

    public static Connection getConnection() {
        return connection;
    }


    public void work(){
        try {
            semaphore.acquire();
            doWork();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    private void doWork() throws InterruptedException {

        synchronized (this) {
            queryCount++;
            totalCountQuery++;
        }

        System.out.println("Current count query's: "+getQueryCount());
        Thread.sleep(random.nextInt(500)+1000);

        synchronized (this) {
            queryCount--;
        }
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void showTotalCountQuery(){
        System.out.println("Total count query is "+totalCountQuery);
    }
}
