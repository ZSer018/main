package threads.threads_12_reentantlock_exchanger_phaser.dictionary;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class FileReaderThread implements Runnable {

    private static Map<String, Thread> threadMap = new IdentityHashMap<>();

    private final String startFrom;
    private final Set<String> wordCollection = new TreeSet<>();
    private final String readableFile;
    private long startPos = 0;

    public FileReaderThread(String startFrom, String readableFile, long startPos) {
        this.startFrom = startFrom;
        this.readableFile = readableFile;
        this.startPos = startPos;
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader( new FileReader( new File("")));
            BufferedWriter writer = new BufferedWriter( new FileWriter( new File("")));
            reader.skip(startPos);

            String s;
            while ( (s = reader.readLine() ) != null ) {
                parseLine(s.split(" "));
            }

            if (wordCollection.size() > 0) {
                writer.write("");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parseLine(String[] words){
        for (int i = 0; i < words.length; i++) {
                if (words[i].startsWith(startFrom)) {
                    wordCollection.add(words[i]);
                } else {
                    if (!threadMap.containsKey(words[i].toLowerCase())) {
                        threadMap.put(words[i].toLowerCase(),
                                new Thread(new FileReaderThread(String.valueOf(words[i].toLowerCase().charAt(0)), readableFile, startPos))
                        );
                    }
                }
        }

    }

}
