package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.dictionary.file_reader;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
         long d =  System.currentTimeMillis();

/*         Thread reader = new Thread(new FileParserMultiThread("а", "E:\\1\\file.txt", 0));
         reader.start();

         while (FileParserMultiThread.getThread() != 0){
             Thread.sleep(1);
         }*/


         Thread reader = new Thread(new FileParserSingleThread( "a","D:\\1\\file_NEW.txt"));
         reader.start();
         reader.join();

         System.out.println( (System.currentTimeMillis() - d) );
    }
}
