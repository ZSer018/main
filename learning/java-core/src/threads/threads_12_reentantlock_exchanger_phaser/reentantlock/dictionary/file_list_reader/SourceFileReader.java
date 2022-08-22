package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.dictionary.file_list_reader;


import java.io.*;

public class SourceFileReader implements Runnable{

    private final String sourceFile;

    public SourceFileReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public void run() {
       readSourceFile();
       System.out.println("---- READER END ---- "+ MyDictionary.getDictionarySize());
    }

    private void readSourceFile(){

        try (BufferedReader listReader =  new BufferedReader( new FileReader(new File(sourceFile)))) {

            String s;
            while ((s = listReader.readLine()) != null) {
                if (s.length() > 0) {
                    MyDictionary.addString(s);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
