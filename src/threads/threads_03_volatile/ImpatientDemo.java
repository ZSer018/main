package threads.threads_03_volatile;

public class ImpatientDemo {

    private static volatile boolean finish = false;

    Runnable impatientThread = () -> {
        System.out.println(">>>>Старт нетерпеливого потока<<<<");

        while (!finish){
        }

        System.out.println("Подсчет закончен!!");
        System.out.println("----Завершение нетерпеливого потока----");

    };

    Runnable counterThred = () -> {
        System.out.println(">>>>Старт потока считателя<<<<");
        for (int i = 1; i < 10; i++) {
            System.out.println(i);
        }
        finish = true;
        System.out.println("----Завершение потока считателя----");
    };


    public static void main(String[] args) throws InterruptedException {
        Thread impatient = new Thread(new ImpatientDemo().impatientThread);
        impatient.start();

        Thread.sleep(100);

        Thread counter = new Thread(new ImpatientDemo().counterThred);
        counter.start();


    }
}


