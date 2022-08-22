package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.printer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintCharThread implements Runnable{

    private final Printer printer;
    private final char requestChar;
    private final char printedChar;

    private static Lock lock = new ReentrantLock();
    private static Condition lockObject = lock.newCondition();

    public PrintCharThread(Printer printer, char requestChar, char printedChar) {
        this.printer = printer;
        this.requestChar = requestChar;
        this.printedChar = printedChar;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                lock.lock();
                while (printer.getPrintChar() != requestChar) {
                    lockObject.await();
                }
                printer.setCharAndPrint(printedChar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockObject.signalAll();
                lock.unlock();
            }
        }
    }
}
