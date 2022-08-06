package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.printer;

public class Printer{

    private char printChar = 'C';

    public char getPrintChar() {
        return printChar;
    }

    public void setCharAndPrint(char printChar) {
        System.out.println(printChar);
        this.printChar = printChar;
    }
}
