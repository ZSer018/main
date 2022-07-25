package threads.threads_08_deadlock.how_to_do_it;

class DeadLockThread implements Runnable {

    private final Object lockA;
    private final Object lockB;
    private final String name,
            objA,
            objB;

    public DeadLockThread(Object lockA, Object lockB, String name, String objA, String objB) {
        this.lockA = lockA;
        this.lockB = lockB;
        this.name = name;
        this.objA = objA;
        this.objB = objB;
    }

    // TODO nested synchronization blocks usually indicate that something has been done wrong.
    @Override
    public void run() {
        while (!Main.start){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        synchronized (lockA){
            System.out.println("Поток "+name+" заблокировал объект "+objA);
            System.out.println("Поток "+name+" пытается получить доступ к объекту "+objB);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (lockB){
            System.out.println("Поток "+name+" успешно заблокировал объекту "+objB);
        }
        System.out.println("Поток "+name+" завершил работу");
    }
}
