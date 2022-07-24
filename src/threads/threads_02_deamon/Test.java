package threads.threads_02_deamon;

class Test{

    public static void main(String[] args) {
        System.out.println("Старт основного потока");
        Thread thread = new Thread(new ChildThread());
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 5; i++) {
            System.out.println("Основной поток:  i = "+i);
        }

        System.out.println("Конец основного потока");
    }
}
