package locks;

import java.util.concurrent.locks.ReentrantLock;

class Cooking extends Thread {
    static int carrotCount, tomatoCount = 0;
    static ReentrantLock locker = new ReentrantLock();

    private void addTomato() {
        locker.lock();
        System.out.println(locker.getHoldCount());
        tomatoCount++;
        locker.unlock();
    }

    private void addCarrot() {
        locker.lock();
        carrotCount++;
        addTomato();
        locker.unlock();
    }

    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            addTomato();
            addCarrot();
        }

    }

}
public class ReentrantLockExample {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new Cooking();
        Thread chefB = new Cooking();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();

        System.out.println("The final needed tomato count is: " + Cooking.tomatoCount);
        System.out.println("The final needed carrot count is: " + Cooking.carrotCount);
    }
}
