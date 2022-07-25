package threads.threads_10_notify_wait_sync_method.post_office;

import java.util.LinkedList;
import java.util.Random;

public class China extends Thread{

    private final PostOffice postOffice;

    public China(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

    private void sendAPackage(LinkedList<String> postPackeges){
        postOffice.loadPostPackages(postPackeges);
        postPackeges.clear();
    }

    @Override
    public void run() {
        super.run();
        LinkedList<String> mailOrder = new LinkedList<>();
        Random r = new Random();
        int count = 1;

        try {
            while (!Thread.interrupted()) {
                synchronized (postOffice) {
                    int x = r.nextInt(15) + 1;
                    for (int i = 0; i < x; i++) {
                        mailOrder.add("order № " + count);
                        count++;
                    }

                    sendAPackage(mailOrder);
                    postOffice.notifyAll();
                }

                sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("bb");
            return;
        }
    }
}
