import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    private static Lock lock1 = new ReentrantLock(true);
    private static Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                deadlock.worker1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                deadlock.worker2();
            }
        });
        t1.start();
        t2.start();
    }

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquires the lock1...");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock2.lock();
        System.out.println("Worker1 acquired the lock2...");

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {

        lock1.lock();
        System.out.println("Worker2 acquires the lock1...");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock2.lock();
        System.out.println("Worker2 acquired the lock2...");

        lock1.unlock();
        lock2.unlock();
    }
}
