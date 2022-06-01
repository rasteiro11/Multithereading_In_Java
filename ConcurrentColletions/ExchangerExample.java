import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {

        Exchanger<Integer> exchanger = new Exchanger<>();

        FirstThread t1 = new FirstThread(exchanger);
        SecondThread t2 = new SecondThread(exchanger);

        new Thread(t1).start();
        new Thread(t2).start();

    }
}

class FirstThread implements Runnable {

    public FirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    private int counter;
    private Exchanger<Integer> exchanger;

    @Override
    public void run() {
        while (true) {
            counter++;
            System.out.println("FirstThread incremented the counter: " + counter);
            try {
                counter = exchanger.exchange(counter);
                System.out.println("FirstThread get the counter: " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}

class SecondThread implements Runnable {

    public SecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    private int counter;
    private Exchanger<Integer> exchanger;

    @Override
    public void run() {
        while (true) {
            counter--;
            System.out.println("SecondThread decremented the counter: " + counter);
            try {
                counter = exchanger.exchange(counter);
                System.out.println("SecondThread get the counter: " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
