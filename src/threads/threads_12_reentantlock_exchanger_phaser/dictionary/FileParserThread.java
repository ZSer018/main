package threads.threads_12_reentantlock_exchanger_phaser.dictionary;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileParserThread implements Runnable {

    private static final ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static final Lock readLock = reentrantLock.readLock();
    private static final Lock writeLock = reentrantLock.readLock();
    private static final Map<String, Thread> threadMap = new HashMap<>();
    private static int thread = 0;

    private final int name;
    private final String startFrom;
    private final Set<String> foundWords = new TreeSet<>();
    private final String readableFile;
    private final StringBuilder stringBuilder = new StringBuilder();
    private long startPos = 0;

    public FileParserThread(String startFrom, String readableFile, long startPos) {
        this.startFrom = startFrom;
        this.readableFile = readableFile;
        this.startPos = startPos;
        this.name = thread;
    }

    @Override
    public void run() {

        readSourceFile();

        if (foundWords.size() > 0) {
            writeSharedFile(readSharedFile());
        }

       //System.out.println("Thread_"+name + " hunted: '"+startFrom+"'  found: "+foundWords.size());
    }

    private void readSourceFile(){
        try (BufferedReader sourceFileReader = new BufferedReader( new FileReader( new File(readableFile)))) {
            sourceFileReader.skip(startPos);

            String s;
            while ( (s = sourceFileReader.readLine() ) != null ) {
                parseLine(s.split("\\s"));
                startPos += s.length();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long readSharedFile(){
        readLock.lock();
        long len = 0;
        String s;

        try ( BufferedReader reader = new BufferedReader( new FileReader( new File(readableFile)))){
            while ((s = reader.readLine()) != null){
                len += s.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }

        return len;
    }

    private void writeSharedFile(long skip){
        if (skip <=0 ) return;

        writeLock.lock();
       // System.out.println(name+" пробует записать файл");
        try (BufferedWriter writer = new BufferedWriter( new FileWriter( new File(readableFile).getParent()+"\\"+"result.txt", true))){
            writer.write("-------------------------------------- Thread_"+ name + " result: "+(foundWords.size()-1)+" ------------------------------------");
                foundWords.forEach(s1 -> {
                    try {
                        writer.write(s1+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

      } catch (IOException e) {
            e.printStackTrace();
      }finally {
            writeLock.unlock();
        }
    }

    private void parseLine(String[] words){
        for (String word : words) {

            if (word.length() == 0) continue;

            if (word.startsWith(startFrom)) {
                foundWords.add(normalizeWord(word));
                if (startFrom.equals("a")){

                }
            } else {
                String key = String.valueOf(word.toLowerCase().charAt(0));

                if (key.matches("[a-zA-Zа-яА-Я]")) {
                    synchronized (threadMap) {
                        if (!threadMap.containsKey(key)) {
                            Thread newReader = new Thread(new FileParserThread(key, readableFile, startPos));
                            thread++;
                            newReader.start();
                            threadMap.put(key, newReader);
                        }
                    }
                }
            }
       }
    }


    private String normalizeWord(String word){
        if (word.endsWith("[a-zA-Zа-яА-Я]")) {
            return word;
        }

        stringBuilder.delete(0, stringBuilder.length());

        for (int i = 0; i < word.length(); i++) {
            if (Character.isAlphabetic(word.charAt(i))) {
                stringBuilder.append(word.charAt(i));
            } else break;
        }

        return stringBuilder.toString();
    }


}
