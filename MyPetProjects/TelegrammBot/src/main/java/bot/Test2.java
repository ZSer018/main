package bot;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test2 {
    private static void run() {
        System.out.println("Running: " + new java.util.Date());
    }

    public static void main(String[] args) throws InterruptedException {
/*        ScheduledExecutorService executorService;
        executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(Test2::run, 0, 29, TimeUnit.MINUTES);*/

        Date date = new Date();
        Thread.sleep(1000);
        Date date2 = new Date();

        System.out.println(date.compareTo(date2));
    }





/*    public static void main(String[] args) throws InterruptedException {
            TestClass executor = new TestClass();
            executor.startAsync();
           //Thread.sleep(10000);
            executor.stopAsync();
        }*/


/*    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                String f = new SimpleDateFormat("ss").format(date);

                if (f.equals("10") | f.equals("15") | f.equals("20")) {
                    System.out.println(f);
                }
            }
        }, 0, 86_400_000);
    }*/
}
