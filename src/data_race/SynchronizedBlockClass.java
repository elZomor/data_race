package data_race;

class ShoppingListSyncBlockClass extends Thread {
    static int tomatoCount = 0;

    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            synchronized (ShoppingListSyncBlockClass.class) {
                tomatoCount++;
            }
        }
    }
}

public class SynchronizedBlockClass {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingListSyncBlockClass();
        Thread chefB = new ShoppingListSyncBlockClass();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // No Data race occurs
        System.out.println("The final needed tomato count is: " + ShoppingListSyncBlockClass.tomatoCount);
    }
}
