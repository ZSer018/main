package threads.threads_08;

public class DeadLockDemo {

    public static volatile boolean start = false;

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();

        new DeadLockThread(a, b, "Thread_A", "A", "B").start();
        new DeadLockThread(b, a, "Thread_B", "B", "A").start();

        Thread.sleep(100);
        start = true;
    }
}

;
