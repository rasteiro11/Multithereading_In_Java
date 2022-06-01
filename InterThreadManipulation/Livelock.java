
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Livelock {
    private static Lock lock1 = new ReentrantLock(true);
    private static Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        Livelock livelock = new Livelock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                livelock.worker1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                livelock.worker2();
            }
        });
        t1.start();
        t2.start();
    }

    public void worker1() {
        while (true) {
            try {
                lock1.tryLock(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker1 acquires the lock1");
            System.out.println("Worker1 tries to get lock2");

            if (lock2.tryLock()) {
                System.out.println("Worker1 acquires the lock2...");
                lock2.lock();
            } else {
                System.out.println("Worker1 can not aquire lock2...");
                lock2.unlock();
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        while (true) {
            try {
                lock2.tryLock(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker2 acquires the lock1");
            System.out.println("Worker2 tries to get lock2");

            if (lock1.tryLock()) {
                System.out.println("Worker2 acquires the lock1...");
                lock1.unlock();
            } else {
                System.out.println("Worker2 can not aquire lock1...");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}
