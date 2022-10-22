package bot.newbot.dbservise;

import bot.newbot.objects.SignUpObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface DBService {

    Map<Long, HashMap<String, String>> getCustomers();

    void signUpCustomer(SignUpObject signUpObject);

    Map<Integer, ArrayList<String>> getPortfolio();

    void addImageToPortfolio();

    Map<String, LinkedHashMap<String, String>> getServiceCalendar();

    void serviceCalendarExtension(Map<String, LinkedHashMap<String, String>> regCalendarMap) throws ParseException;

    Map<Long, ArrayList<String>> getCustomersManicureRegistration();

    void regCustomerForManicure();

    void dropDB();
    void createDB();
}
