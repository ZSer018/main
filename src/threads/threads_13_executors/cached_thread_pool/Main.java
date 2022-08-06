package threads.threads_13_executors.cached_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            executorService.submit(new MyThread(i));
        }

        executorService.shutdown();

    }
}
