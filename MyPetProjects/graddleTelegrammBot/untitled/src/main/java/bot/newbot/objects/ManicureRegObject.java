package bot.newbot.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
public class ManicureRegObject {

    private int step = -1;

    private String manicureType;
    private int cost;
    private Date date;
    private Date time;


    private HashMap<String, String> regTypes;
    private String chosenType;

    private HashMap<String, String> weeks;
    private String chosenWeek;

    private LinkedHashMap<String, LinkedHashMap<String, String>> days;
    private String chosenDay;

    private ArrayList<String> hours;
    private String chosenHour;
}
