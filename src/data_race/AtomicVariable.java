package data_race;


import java.util.concurrent.atomic.AtomicInteger;

class ShoppingListAtomic extends Thread{
    static AtomicInteger tomatoCount = new AtomicInteger(0);

    public void run() {

        for (int i = 0; i < 10_000_000; i++) {
            tomatoCount.incrementAndGet(); // Equivalent to tomatoCount++
        }
    }
}


public class AtomicVariable {

    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingListAtomic();
        Thread chefB = new ShoppingListAtomic();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // No Data race occurs
        System.out.println("The final needed tomato count is: " + ShoppingListAtomic.tomatoCount);
    }

}
