package data_race;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShoppingListMutEx extends Thread{
    static int tomatoCount = 0;
    static Lock pencil = new ReentrantLock();

    public void run() {
        for (int i = 0; i < 5; i++) {
            pencil.lock();
            tomatoCount++;
            pencil.unlock();
            System.out.println(Thread.currentThread().getName() + " is now thinking");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


public class MutEx {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingListMutEx();
        Thread chefB = new ShoppingListMutEx();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // No Data race occurs
        System.out.println("The final needed tomato count is: " + ShoppingListMutEx.tomatoCount);
    }
}
