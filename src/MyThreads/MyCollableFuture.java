package threadsInJava;

import java.util.Random;
import java.util.concurrent.*;

public class MyCollableFuture {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);


            Future future = executorService.submit(new Callable<String>() {

                Random random = new Random();

                @Override
                public String call() throws Exception {

                    if (random.nextInt(10) < 5) {
                        return "Hello";
                    } else {
                        throw new Exception("Throw: That's bye..");
                    }
                }
            });

        executorService.shutdown();

        try{
            System.out.println(future.get());
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            Throwable ex = e.getCause();
            System.out.println(ex.getMessage());
        }


    }
}
