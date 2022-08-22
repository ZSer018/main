package threads.threads_08_deadlock.how_not_to_do;

public class Main {

    public static volatile boolean start = false;

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();

        new Thread(new DeadLockThread(a, b, "Thread_A", "A", "B")).start();
        new Thread(new DeadLockThread(b, a, "Thread_B", "B", "A")).start();

        Thread.sleep(100);
        start = true;
    }
}

;
