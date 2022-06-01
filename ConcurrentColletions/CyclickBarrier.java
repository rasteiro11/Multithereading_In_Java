import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclickBarrier {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("All taks have been finished...");
            }
        });
        for (int i = 0; i < 5; ++i)
            service.execute(new BarrierWorker(i + 1, barrier));
        service.shutdown();
    }
}

class BarrierWorker implements Runnable {
    private int id;
    private Random random;
    private CyclicBarrier barrier;

    public BarrierWorker(int id, CyclicBarrier barrier) {
        this.id = id;
        this.random = new Random();
        this.barrier = barrier;
    }

    @Override
    public void run() {
        doWork();

    }

    private void doWork() {
        System.out.println("Thread with ID " + this.id + " starts the work...");
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("After running the await method");
    }

}
