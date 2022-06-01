public class App {

    public static int counter1 = 0;
    public static int counter2 = 0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    // usually it is not a good practice to use synchronized keyword
    public static void increment1() {
        // class level locking
        synchronized (lock1) {
            counter1++;
        }
    }

    public static synchronized void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    public static void main(String[] args) {

        // process();

        Process process = new Process();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    process.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i)
                    increment1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i)
                    increment2();

            }

        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The counter1 is: " + counter1);
        System.out.println("The counter1 is: " + counter2);
    }

}

class Process {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait();
            System.out.println("Again in the producer method");

        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("consume method is executed");
            notify();
        }
    }
}
