package threads.threads_10_notify_wait_sync_method;

public class JoinTest {

    static class Th extends Thread{
        @Override
        public void run() {
            super.run();

            System.out.println(this);

            try {
                Thread.sleep(1000);
                this.notify();

                Thread.sleep(3000);
                System.out.println("Th quit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Th th = new Th();
        th.start();

        System.out.println(th);

        try {
            th.join();
        } catch (InterruptedException e) {
            System.out.println("Notify");
        }

        System.out.println("Main thread quit");
    }

}
