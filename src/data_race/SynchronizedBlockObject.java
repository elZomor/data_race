package data_race;

class Tomato {
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class ShoppingListSyncBlockObject extends Thread{
    static final Tomato tomato = new Tomato();

    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            synchronized (tomato) {
                tomato.setCount(tomato.getCount() + 1);
            }
        }
    }
}

public class SynchronizedBlockObject {
    public static void main(String[] args) throws InterruptedException {
        // We will make two threads to change the class variable (static variable)
        Thread chefA = new ShoppingListSyncBlockObject();
        Thread chefB = new ShoppingListSyncBlockObject();

        chefA.start();
        chefB.start();

        chefA.join();
        chefB.join();
        // No Data race occurs
        System.out.println("The final needed tomato count is: " + ShoppingListSyncBlockObject.tomato.getCount());
    }
}
