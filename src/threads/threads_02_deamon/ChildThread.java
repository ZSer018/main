package threads.threads_02_deamon;

public class ChildThread extends Thread{
    @Override
    public void run() {
        super.run();

        System.out.println("Начало выполнения дочернего потока.");
        for (int i = 0; i < 20; i++) {
            System.out.println("Дочерний поток:  i = "+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Конец выполнения дочернего потока.");
    }
}



