package liveness;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class PhilosopherDeadLock extends Thread {
    private final ReentrantLock firstChopStick, secondChopStick;
    private static int sushiCount = 50_000_000;


    PhilosopherDeadLock(String name, ReentrantLock firstChopStick, ReentrantLock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            firstChopStick.lock();
            secondChopStick.lock();

            if (sushiCount > 0) {
                sushiCount--;
                System.out.println(this.getName() + " took a piece, remaining: " + sushiCount);
            }

            secondChopStick.unlock();
            firstChopStick.unlock();

        }
    }
}

class PhilosopherGivesChopstick extends Thread {
    private final ReentrantLock firstChopStick, secondChopStick;
    private static int sushiCount = 50_000_000;

    PhilosopherGivesChopstick(String name, ReentrantLock firstChopStick, ReentrantLock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            firstChopStick.lock();
            if (! secondChopStick.tryLock()) {
                firstChopStick.unlock();
                try {
                    Thread.sleep(new Random().nextInt(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (sushiCount > 0) {
                sushiCount--;
                System.out.println(this.getName() + " took a piece, remaining: " + sushiCount);
            }

            secondChopStick.unlock();
            firstChopStick.unlock();

        }
    }
}

public class DeadLock {


    public static void main(String[] args) {
        ReentrantLock chopStickA = new ReentrantLock();
        ReentrantLock chopStickB = new ReentrantLock();
        ReentrantLock chopStickC = new ReentrantLock();

        // Dead lock
//        new PhilosopherDeadLock("Ph-A", chopStickA, chopStickB).start();
//        new PhilosopherDeadLock("Ph-B", chopStickB, chopStickC).start();
//        new PhilosopherDeadLock("Ph-C", chopStickC, chopStickA).start();

        // Solution by prioritizing chopsticks (locks) as A-B-C
//        new PhilosopherDeadLock("Ph-A", chopStickA, chopStickB).start();
//        new PhilosopherDeadLock("Ph-B", chopStickB, chopStickC).start();
//        new PhilosopherDeadLock("Ph-C", chopStickA, chopStickC).start();

        // Solution by unlocking after amount of time
        new PhilosopherGivesChopstick("Ph-A", chopStickA, chopStickB).start();
        new PhilosopherGivesChopstick("Ph-B", chopStickB, chopStickC).start();
        new PhilosopherGivesChopstick("Ph-C", chopStickC, chopStickA).start();

    }
}
