package dataRace;

class ShoppingListSyncMethod extends Thread{
    static int tomatoCount = 0;

    private static synchronized void addTomato() {
        tomatoCount++;
    }

    public void run() {

        for (int i = 0; i < 10_000_000; i++) {
            addTomato();
        }
    }
}

public class SynchronizedMethod {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingListSyncMethod();
        Thread chefB = new ShoppingListSyncMethod();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // No Data race occurs
        System.out.println("The final needed tomato count is: " + ShoppingListSyncMethod.tomatoCount);
    }
}
