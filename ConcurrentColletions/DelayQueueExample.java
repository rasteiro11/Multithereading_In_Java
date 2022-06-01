import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

    public static void main(String[] args) {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();
        try {
            queue.put(new DelayedWorker("This is the first message...", 2000));
            queue.put(new DelayedWorker("This is the second message...", 10000));
            queue.put(new DelayedWorker("This is the third message...", 4500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // we can get the messages
        while (!queue.isEmpty()) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DelayedWorker implements Delayed {

    @Override
    public String toString() {
        return "DelayWorker [message=" + message + "]";
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private long duration;
    private String message;

    public DelayedWorker(String message, long duration) {
        this.duration = System.currentTimeMillis() + duration;
        this.message = message;
    }

    @Override
    public int compareTo(Delayed other) {
        // this is the method that can compare with objects
        // -1, +1, 0
        if (duration < ((DelayedWorker) other).getDuration())
            return -1;
        if (duration > ((DelayedWorker) other).getDuration())
            return +1;
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

}
