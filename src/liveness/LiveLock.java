package liveness;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

//class PhilosopherDeadLock extends Thread {
//    private final ReentrantLock firstChopStick, secondChopStick;
//    private static int sushiCount = 50_000_000;
//
//
//    PhilosopherDeadLock(String name, ReentrantLock firstChopStick, ReentrantLock secondChopStick) {
//        this.setName(name);
//        this.firstChopStick = firstChopStick;
//        this.secondChopStick = secondChopStick;
//    }
//
//    public void run() {
//        while (sushiCount > 0) {
//            firstChopStick.lock();
//            secondChopStick.lock();
//
//            if (sushiCount > 0) {
//                sushiCount--;
//                System.out.println(this.getName() + " took a piece, remaining: " + sushiCount);
//            }
//
//            secondChopStick.unlock();
//            firstChopStick.unlock();
//
//        }
//    }
//}

class PhilosopherGivesChopstickLiveLock extends Thread {
    private final ReentrantLock firstChopStick, secondChopStick;
    private static int sushiCount = 50_000;
    private final Random rand = new Random();

    PhilosopherGivesChopstickLiveLock(String name, ReentrantLock firstChopStick, ReentrantLock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            firstChopStick.lock();
            if (! secondChopStick.tryLock()) {
                // Causes live lock
                // To resolve this, check deadlock solution
                System.out.println(this.getName() + " has released the lock");
                firstChopStick.unlock();
            } else {
                if (sushiCount > 0) {
                    sushiCount--;
                    System.out.println(this.getName() + " took a piece, remaining: " + sushiCount);
                }

                secondChopStick.unlock();
                firstChopStick.unlock();
            }
        }
    }
}

public class LiveLock {
    public static void main(String[] args) {
        ReentrantLock chopStickA = new ReentrantLock();
        ReentrantLock chopStickB = new ReentrantLock();
        ReentrantLock chopStickC = new ReentrantLock();

        new PhilosopherGivesChopstickLiveLock("Ph-A", chopStickA, chopStickB).start();
        new PhilosopherGivesChopstickLiveLock("Ph-B", chopStickB, chopStickC).start();
        new PhilosopherGivesChopstickLiveLock("Ph-C", chopStickC, chopStickA).start();

    }
}
