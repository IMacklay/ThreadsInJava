package MyThreads.SynchronyzedThreads;

public class SynchronyzedThreads {
    public static void main(String[] args) {

        BankCount bankCount = new BankCount();
        Work people = new Work(bankCount);

        Thread thread1 = new Thread(people);
        Thread thread2 = new Thread(people);

        thread1.setName("Tony");
        thread2.setName("Lora");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }

        System.out.println("Баланс по карте составляет: "+bankCount.getBallance());

    }
}

class Work implements Runnable{

    BankCount operation;

    public Work(BankCount operation){
        this.operation = operation;
    }

    public void run(){

        int SumWithDrawal = 499;

        for (int i=0; i<5; i++){

           operation.makeWithdrawMoney(SumWithDrawal);

        }

    }

}

class BankCount{
    private int clientID;
    private volatile int ballance;

    public BankCount(){
        clientID = (int) Math.random() * 100 + 1;
        ballance = 1000;
    }

    public int getBallance(){
        return ballance;
    }

    //synchronized - помогает людям не влезать в долги
    public void makeWithdrawMoney(int sum){

        String agent = Thread.currentThread().getName();

        if (this.ballance>=sum){
            System.out.println(agent+" - собирается снять "+sum+" рупий..");
            try{
                Thread.sleep(5);
                System.out.println(agent+" - задремал");
            }catch (InterruptedException ex){
                System.out.println("При попытке поспать у "+agent+" что-то пошло не так..");
            }
            withDrawal(sum);
            System.out.println(Thread.currentThread().getName()+" - успешно снял(-ла) со счёта "+sum+" рупий..");
        } else {
            System.out.println("Недостаточно средств на счете!");
        }

    }

    private void withDrawal(int sum){
        this.ballance -= sum;
    }

}