package liveness;

import java.util.concurrent.locks.ReentrantLock;

class PhilosopherAbandonedLock extends Thread {
    private final ReentrantLock firstChopStick, secondChopStick;
    private static int sushiCount = 50_000;


    PhilosopherAbandonedLock(String name, ReentrantLock firstChopStick, ReentrantLock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            firstChopStick.lock();
            secondChopStick.lock();
            // Solve by adding try-catch block around the critical section
            // Add the unlock part in the "finally" block
            try {
                if (sushiCount > 0) {
                    sushiCount--;
                    System.out.println(this.getName() + " took a piece, remaining: " + sushiCount);
                }

                if (sushiCount == 10)
                    // This throws exception, without try-catch block will cause abandonment lock
                    System.out.println(1/0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                secondChopStick.unlock();
                firstChopStick.unlock();
            }
        }
    }
}

public class AbandonedLock {
    public static void main(String[] args) {
        ReentrantLock chopStickA = new ReentrantLock();
        ReentrantLock chopStickB = new ReentrantLock();
        ReentrantLock chopStickC = new ReentrantLock();

        // Abandonment Lock
        new PhilosopherAbandonedLock("Ph-A", chopStickA, chopStickB).start();
        new PhilosopherAbandonedLock("Ph-B", chopStickB, chopStickC).start();
        new PhilosopherAbandonedLock("Ph-C", chopStickA, chopStickC).start();
    }
}
