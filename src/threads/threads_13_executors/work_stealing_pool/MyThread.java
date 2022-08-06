package threads.threads_13_executors.work_stealing_pool;

import java.util.Random;

public class MyThread implements Runnable {

    private final int name;

    public MyThread(int name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Поток "+ name + " выполняется");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
