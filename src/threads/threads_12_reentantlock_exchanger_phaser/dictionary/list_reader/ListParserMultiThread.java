package threads.threads_12_reentantlock_exchanger_phaser.dictionary.list_reader;

import threads.threads_12_reentantlock_exchanger_phaser.dictionary.file_reader.FileParserMultiThread;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListParserMultiThread implements Runnable {

    private static final ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static final Lock writeLock = reentrantLock.writeLock();

    private static final Map<String, Thread> threadMap = new HashMap<>();
    private static final AtomicInteger thread = new AtomicInteger(0);
    private static volatile boolean createNewFile = true;

    private final int name;
    private final String startFrom;
    private final Set<String> foundWords = new TreeSet<>();
    private final String readableFile;
    private final StringBuilder stringBuilder = new StringBuilder();
    private long startPos = 0;

    public static int getThread() {
        return thread.get();
    }

    public ListParserMultiThread(String startFrom, String readableFile, long startPos) {
        this.startFrom = startFrom;
        this.readableFile = readableFile;
        this.startPos = startPos;
        this.name = thread.incrementAndGet();

        if (createNewFile) {
            String file = new File(readableFile).getParent() + "\\" + "result_multi.txt";
            File file1 = new File(file);
            if (file1.exists()) {
                file1.delete();
            }
            createNewFile = false;
        }
    }

    @Override
    public void run() {
        readList();

        if (foundWords.size() > 0) {
            try {
                writeLock.lock();
                writeResultInFile();
            } finally {
                writeLock.unlock();
            }
        }

        thread.getAndDecrement();
    }

    private void readList(){
        int i = 0;
        String s;


        while ( true ) {
          if ((s = MyDictionary.getString(i)) == null) {
               break;
           }

           parseLine(s.split("\\s"));
           i++;
        }
    }

    private void parseLine(String[] words) {

        for (String word : words) {
            if (word.length() == 0) continue;

            if (word.startsWith(startFrom)) {
                foundWords.add(normalizeWord(word));
            } else {
                char c = word.toLowerCase().charAt(0);
                String strS = String.valueOf(c);

                if (!threadMap.containsKey(strS)) {
                    try {
                        writeLock.lock();
                        if (!threadMap.containsKey(strS) && Character.isAlphabetic(c)) {
                            Thread newReader = new Thread(new ListParserMultiThread(strS, readableFile, startPos));
                            newReader.start();
                            threadMap.put(strS, newReader);
                        }
                    } finally {
                        writeLock.unlock();
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

    private void writeResultInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(readableFile).getParent() + "\\" + "result_multi.txt", true))) {
            writer.write("-------------------------------------- Thread_" + name + ", hunted '" + startFrom + "',  result: " + (foundWords.size()) + " ------------------------------------\n");
            foundWords.forEach(s1 -> {
                try {
                    writer.write(s1 + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
