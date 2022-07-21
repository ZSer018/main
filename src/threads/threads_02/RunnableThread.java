package ThreadsLessons.threads_02;

public class RunnableThread {

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("Начало выполнения дочернего потока.");
            for (int i = 0; i < 10; i++) {
                System.out.println("i = "+i);
            }
            System.out.println("Конец выполнения дочернего потока.");
        }
    };

    public static void main(String[] args) {
      System.out.println("Старт основного потока");
      Thread thread = new Thread(runnable);
      thread.start();
      System.out.println("Конец основного потока");
    }
}


