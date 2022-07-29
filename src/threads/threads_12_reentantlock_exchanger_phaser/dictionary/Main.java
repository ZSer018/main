package threads.threads_12_reentantlock_exchanger_phaser.dictionary;

public class Main {

    public static void main(String[] args) {
        new Thread(new FileParserThread("а", "D:\\1\\file.txt", 0)).start();

        String key = "верблюда";

        if (key.matches("[a-zA-Z]")) {
            System.out.println("yes");
        } else
            System.out.println("NO");

    }

}
