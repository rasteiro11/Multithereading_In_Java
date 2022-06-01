import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new PriorityBlockingQueue<>();

        FirstWorkerPriorityBlockingQueue first = new FirstWorkerPriorityBlockingQueue(queue);
        SecondWorkerPriorityBlockingQueue second = new SecondWorkerPriorityBlockingQueue(queue);

        new Thread(first).start();
        new Thread(second).start();
    }
}

class FirstWorkerPriorityBlockingQueue implements Runnable {

    public FirstWorkerPriorityBlockingQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    private BlockingQueue<String> queue;

    @Override
    public void run() {
        try {
            queue.put("B");
            queue.put("H");
            queue.put("F");
            Thread.sleep(2000);
            queue.put("A");
            Thread.sleep(2000);
            queue.put("Z");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class SecondWorkerPriorityBlockingQueue implements Runnable {

    public SecondWorkerPriorityBlockingQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    private BlockingQueue<String> queue;

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            Thread.sleep(2000);
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
