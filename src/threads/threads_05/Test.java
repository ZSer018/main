package ThreadsLessons.threads_05;

class Test {
    public static void main(String[] args) throws InterruptedException {
        Worker worker1 = new Worker();
        worker1.start();
        Worker2 worker2 = new Worker2();
        worker2.start();
        Thread.sleep(100);

        while (worker1.isAlive() | worker2.isAlive()){
            if (!worker1.isInterrupted()){
                System.out.println("Главный поток:  ----- Смена закончена. Worker_1, прекращай работу! ----");
                worker1.interrupt();
                Thread.sleep(900);
            }

            if (!worker2.isInterrupted()){
                System.out.println("Главный поток:  ----- Смена закончена. Worker_2, прекращай работу! ----");
                worker2.interrupt();
                Thread.sleep(900);
            }
        }
    }
}
