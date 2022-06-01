import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}

class Processor {

    private List<Integer> list = new ArrayList<Integer>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private int value = 0;

    public void producer() throws InterruptedException {
        synchronized (this) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (this) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    value = 0;
                    wait();
                } else {
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
