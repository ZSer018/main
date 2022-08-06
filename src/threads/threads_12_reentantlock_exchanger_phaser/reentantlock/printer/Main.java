package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.printer;

public class Main {

    private final static Printer printer = new Printer();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new PrintCharThread(printer, 'A', 'B')).start();
        new Thread(new PrintCharThread(printer, 'B', 'C')).start();
        new Thread(new PrintCharThread(printer, 'C', 'A')).start();
    }
}
