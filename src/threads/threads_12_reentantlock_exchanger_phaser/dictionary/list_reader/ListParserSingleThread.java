package threads.threads_12_reentantlock_exchanger_phaser.dictionary.list_reader;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListParserSingleThread implements Runnable {

    private final String startFrom;
    private final Map<String, Set<String>> foundWords = new HashMap<>();
    private final String sourceFile;
    private final StringBuilder stringBuilder = new StringBuilder();

    public ListParserSingleThread(String startFrom, String sourceFile) {
        this.startFrom = startFrom;
        this.sourceFile = sourceFile;
        foundWords.put(startFrom, new HashSet<>());

        String file = new File(sourceFile).getParent() + "\\" + "LIST_result_single.txt";
        File file1 = new File(file);
        if (file1.exists()) {
             file1.delete();
        }
    }

    @Override
    public void run() {
        readlist();
        writeResultInFile();
        System.out.println("Thread done");
    }

    private void readlist(){
        int i = 0;
        String s;

        while ( (s = MyDictionary.getString(i)) != null) {
            parseLine(s.split("\\s"));
            i++;
        }
    }

    private void writeResultInFile(){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(sourceFile).getParent() + "\\" + "LIST_result_single.txt", true))) {

            foundWords.forEach((s, strings) -> {
                try {
                    writer.write("-------------------------------------- " + startFrom + "'  result: " + (strings.size()) + " --------------------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                strings.forEach(s1 -> {
                    try {
                        writer.write(s1 + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLine(String[] words){
        for (String word : words) {
            if (word.length() == 0) continue;

            char key = word.toLowerCase().charAt(0);
            String keyS = String.valueOf(key);

            if (Character.isAlphabetic(key)) {
                if (!foundWords.containsKey(keyS)) {
                    foundWords.put(keyS, new HashSet<>());
                }
                foundWords.get(keyS).add(normalizeWord(word.toLowerCase() ));
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
