package threads.threads_12_reentantlock_exchanger_phaser.dictionary.list_reader;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread reader = new Thread( new SourceFileReader("D:\\1\\file.txt"));
        reader.start();
        reader.join();

        long d =  System.currentTimeMillis();


        Thread parserMultiThread = new Thread(new ListParserMultiThread("а", "D:\\1\\file.txt", 0));
        parserMultiThread.start();

        while (ListParserMultiThread.getThread() != 0){
            Thread.sleep(1);
        }


        //Thread parserSingleThread = new Thread(new ListParserSingleThread( "a","D:\\1\\file.txt"));
        //parserSingleThread.start();
        //parserSingleThread.join();

        System.out.println( (System.currentTimeMillis() - d) );
    }

}
