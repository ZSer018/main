package bot.newbot.dbservise.mySQL;

import bot.newbot.dbservise.DBService;
import bot.newbot.objects.SignUpObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySQLUserService implements DBService {

    @Override
    public Map<Long, HashMap<String,String>> getCustomers() {
        return null;
    }

    @Override
    public void signUpCustomer(SignUpObject signUpObject) {

    }

    @Override
    public Map<Integer, ArrayList<String>> getPortfolio() {
        return null;
    }

    @Override
    public void addImageToPortfolio() {

    }

    @Override
    public Map<String, LinkedHashMap<String, String>> getServiceCalendar() {
        return null;
    }

    @Override
    public void serviceCalendarExtension(Map<String, LinkedHashMap<String, String>> regCalendarMap) {

    }

    @Override
    public Map<Long, ArrayList<String>> getCustomersManicureRegistration() {
        return null;
    }

    @Override
    public void regCustomerForManicure() {

    }

    @Override
    public void dropDB() {

    }

    @Override
    public void createDB() {

    }
}
