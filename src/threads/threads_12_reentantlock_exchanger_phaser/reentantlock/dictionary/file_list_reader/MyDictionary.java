package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.dictionary.file_list_reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDictionary {

    private static final List<String> stringList = new ArrayList<>(50_000_000);

    public static String getString(int index){
        return index < stringList.size()? stringList.get(index): null;
    }

    public static void addString(String string){
        stringList.add(string);
    }

    public static int getDictionarySize(){
        return stringList.size();
    }

    public static void save() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("D:\\1\\file_NEW.txt")))) {
                for (int i = 0; i < 10; i++) {
                    stringList.forEach(s -> {
                        try {
                            writer.append(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
