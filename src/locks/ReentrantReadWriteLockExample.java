package locks;


import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

class Chef extends Thread {
    private static int garlicCount = 0;
    private final boolean writer;
    private final static ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
    private final static ReadLock readLocker = locker.readLock();
    private final static WriteLock writeLocker = locker.writeLock();

    Chef(boolean writer){this.writer = writer;}

    public void run() {
        if (writer) {
            writeLocker.lock();
            garlicCount++;
            System.out.println(this.getName() + " Updated garlic count to: " + garlicCount);
            writeLocker.unlock();
        } else {
            readLocker.lock();
            System.out.println("Total current read counts is: " + locker.getReadLockCount());
            readLocker.unlock();
        }

    }

}

public class ReentrantReadWriteLockExample {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Chef(false).start();
        }

        for (int i = 0; i < 5; i++) {
            new Chef(true).start();
        }

    }
}
