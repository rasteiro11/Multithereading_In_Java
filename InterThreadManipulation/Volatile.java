public class Volatile {
    public static void main(String[] args) {
        WorkerVolatile worker = new WorkerVolatile();
        Thread t1 = new Thread(worker);
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
        worker.setTerminated(true);
        System.out.println("Algorithm is terminated");
    }

}

class WorkerVolatile implements Runnable {
    private volatile boolean isTerminated;

    @Override
    public void run() {
        while (!isTerminated) {
            System.out.println("Working class is running...");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

}
