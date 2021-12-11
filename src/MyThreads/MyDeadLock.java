package threadsInJava;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class MyDeadLock {
    public static void main(String[] args) {


        AccAction accAction = new AccAction();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                accAction.FirstThread();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                accAction.SecondThread();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        accAction.finishAction();
    }
}

class AccAction {

    private Account account1 = new Account();
    private Account account2 = new Account();

    private Random random = new Random();

    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();


    private void takeLock(Lock lock1, Lock lock2){
        boolean firstLockAvailable = false;
        boolean secondLockAvailable = false;

        while (true) {
            firstLockAvailable = lock1.tryLock();
            secondLockAvailable = lock2.tryLock();

            if (firstLockAvailable && secondLockAvailable) {
                return;
            }

            if (firstLockAvailable) { //Если получились забрать только первый лок,
                lock1.unlock();       //то возращаем
            }

            if (secondLockAvailable) { // Если забрали только второй лок, -
                lock2.unlock();       // возвращаем
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void FirstThread(){
        for(int i=0; i<100000; i++) {
            takeLock(lock1, lock2);
            Account.transfer(account1, account2, random.nextInt(100));
            lock2.unlock();
            lock1.unlock();
        }
    }

    public void SecondThread(){
        for(int i=0; i<100000; i++) {
            takeLock(lock2, lock1);
            Account.transfer(account2, account1, random.nextInt(100));
            lock1.unlock();
            lock2.unlock();
        }
    }

    public void finishAction(){
        System.out.println("Balance Account1: "+account1.getBalance());
        System.out.println("Balance Account2: "+account2.getBalance());
        System.out.println("Balance Total Accounts: "+(account1.getBalance()+account2.getBalance()));
    }
}

class Account{
    private int balance = 10000;

    public void deposit(int amount){
        balance += amount;
    }

    public void withdrawal(int amount){
        balance -= amount;
    }

    public static void transfer(Account account_source, Account account_target, int amount){
        account_source.withdrawal(amount);
        account_target.deposit(amount);
    }


    public int getBalance() {
        return balance;
    }
}