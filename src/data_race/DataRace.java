package data_race;


class ShoppingList extends Thread{
    static int tomatoCount = 0;

    public void run() {
        int loop_count = 10_000_000;

        for (int i = 0; i < loop_count; i++) {
            // This line is doing three things
            // 1- Reading the current tomatoCount
            // 2- Adding 1 to the read value
            // 3- Writing the current tomatoCount
            tomatoCount++;
        }
    }
}

public class DataRace {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingList();
        Thread chefB = new ShoppingList();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // Data race occurs here at high values
        System.out.println("The final needed tomato count is: " + ShoppingList.tomatoCount);
    }
}
