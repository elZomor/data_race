package locks;


import java.util.concurrent.locks.ReentrantLock;

class CarrotAdder extends Thread {
    private int choppedCarrots = 0;
    private static int carrotsInPot = 0;
    private final static ReentrantLock pot = new ReentrantLock();

    public void run() {
        while (carrotsInPot < 20) {
            if (choppedCarrots > 0 && pot.tryLock()) {
                try {
                    carrotsInPot += choppedCarrots;
                    System.out.println(this.getName() + " has added " + choppedCarrots + " carrots.");
                    choppedCarrots = 0;
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pot.unlock();
                }
            } else {
                try {
                    Thread.sleep(100);
                    choppedCarrots++;
                    System.out.println(this.getName() + " has chopped one more carrot.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


public class TryLockExample {
    public static void main(String[] args) throws InterruptedException {
        Thread chefA = new CarrotAdder();
        Thread chefB = new CarrotAdder();

        long start = System.currentTimeMillis();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();

        long finish = System.currentTimeMillis();

        System.out.println("Elapsed time: " + (float) (finish - start) / 1000 + " seconds.");
    }
}
