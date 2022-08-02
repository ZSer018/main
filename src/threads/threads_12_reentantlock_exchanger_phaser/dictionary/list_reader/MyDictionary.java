package threads.threads_12_reentantlock_exchanger_phaser.dictionary.list_reader;

import java.util.ArrayList;
import java.util.List;

public class MyDictionary {

    private static final List<String> stringList = new ArrayList<>(5_000_000);

    public static String getString(int index){
        return index < stringList.size()? stringList.get(index): null;
    }

    public static void addString(String string){
        stringList.add(string);
    }

    public static int getDictionarySize(){
        return stringList.size();
    }
}
