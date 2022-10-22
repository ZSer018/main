package bot.newbot.data;

import bot.newbot.dbservise.DBService;
import bot.newbot.objects.ManicureRegObject;
import bot.newbot.objects.SignUpObject;
import net.bytebuddy.description.field.FieldList;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager {

    private final DBService dbService;
    private final Map<Long, HashMap<String, String>> customers;
    private final Map<Integer, ArrayList<String>> portfolio;
    private final Map<String, LinkedHashMap<String, String>> manicureFreeDateCalendar;
    private final Map<Long, SignUpObject> userSignUpData;
    private final Map<Long, ManicureRegObject> customersManicureRegistration;


   public DataManager(DBService dbService) {
       this.dbService = dbService;

       customers = dbService.getCustomers();
       portfolio = dbService.getPortfolio();
       manicureFreeDateCalendar = dbService.getServiceCalendar();
       //customersManicureRegistration = dbService.getCustomersManicureRegistration();
       customersManicureRegistration = new HashMap<>();

       userSignUpData = new HashMap<>();
   }

    public boolean userPresent(long userId){
       return customers.get(userId) != null;
    }

    public void computeSignUpDataIfAbsent(long userId){
        userSignUpData.computeIfAbsent(userId, k -> new SignUpObject());
    }

    public void setSignUpAwaitUserDataInput(long userId, boolean await){
        userSignUpData.get(userId).setAwaitingDataInput(await);
    }

    public boolean userReceivePersonalData(long userId){
        return userSignUpData.get(userId) != null && userSignUpData.get(userId).isAwaitingDataInput();
    }

    public int getManicureRegistrationProgress(long userId){
       if (customersManicureRegistration.get(userId) == null){
           ManicureRegObject manicureRegObject = new ManicureRegObject();
           manicureRegObject.setStep(1);
           customersManicureRegistration.put(userId, manicureRegObject);
       }
        return customersManicureRegistration.get(userId).getStep();
    }

    public ManicureRegObject manicureRegistrationGetObject(long userId){
        return customersManicureRegistration.get(userId);
    }

    public void manicureRegistrationSetObject(long userId, ManicureRegObject regObject){
       regObject.setStep(regObject.getStep() +1);
        customersManicureRegistration.put(userId, regObject);
    }

    public void signUpUserAddPersonalUserData(long userId, String name, String phone, String telegramContact) {
        var signUpObject = new SignUpObject();
        signUpObject.setName(name);
        signUpObject.setPhone(phone);
        signUpObject.setTelegramId(userId);
        signUpObject.setTgUsername(telegramContact);
        userSignUpData.put(userId, signUpObject);
    }

    public String getSignUpPersonalUserData(long userId){
       return  "Имя: "+ userSignUpData.get(userId).getName() + "\n"+
               "Телефон: "+ userSignUpData.get(userId).getPhone() + "\n"+
               "Контакт: "+ userSignUpData.get(userId).getTgUsername() + "\n";
    }

    public boolean addNewCustomer(long userId){
       if (userSignUpData.get(userId) == null){
           return false;
       }

       try {
           dbService.signUpCustomer(userSignUpData.get(userId));
       } catch (Exception e){
           System.out.println("Error DB insert: "+ e.getMessage());
           return false;
       }

       var user = userSignUpData.get(userId);
       var userdata = new HashMap<String, String>();
       userdata.put("name",user.getName());
       userdata.put("phone",user.getPhone());
       userdata.put("contact",user.getTgUsername());
       customers.put(userId, userdata);
       userSignUpData.remove(userId);
       return true;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getRegDays(long customerId){
       var temp = new LinkedHashMap<String, LinkedHashMap<String, String>>();
       String date = customersManicureRegistration.get(customerId).getChosenWeek();

       int x = 0;
       boolean getSeven = false;
        for (var entry : manicureFreeDateCalendar.entrySet()) {
            if (entry.getKey().equals(date)){
                getSeven = true;
            }
            if (getSeven){
                temp.put(entry.getKey(), entry.getValue());
                x++;
            }
            if (x == 8) {
                break;
            }
        }

       return temp;
    }


    public ArrayList<String> getRegHours(long customerId){
        var temp = new ArrayList<String>();
        String date = customersManicureRegistration.get(customerId).getChosenDay();

        int x = 0;
        boolean getSeven = false;
        var hours = manicureFreeDateCalendar.get(date);
        if (hours.entrySet().stream().anyMatch(e -> e.getValue().equals("+"))){
            hours.entrySet().stream().filter(e -> e.getValue().equals("+")).forEach(e-> temp.add(e.getKey()));

        }
        return temp;
    }


    public HashMap<String, String> getRegWeeks() {
        int x = -1;
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();

        String tempdate = null;

        HashMap<String, String> manicureWeeksData = new HashMap<>();
        for (var entry : manicureFreeDateCalendar.keySet()) {
            x++;
            if (x > 7) {
                x = 0;
            }

            if (x == 0) {
                tempdate = entry;
                calendar.set(1, Integer.parseInt(entry.split("\\.")[1]), 1);
                stringBuilder.append(entry.split("\\.")[2]).append(" ").append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru")));
            }
            if (x == 7) {
                calendar.set(1, Integer.parseInt(entry.split("\\.")[1]), 1);
                stringBuilder.append("  -  ").append(entry.split("\\.")[2]).append(" ").append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru")));

                manicureWeeksData.put(stringBuilder.toString(), tempdate);
                stringBuilder.setLength(0);
            }

        }

        if (stringBuilder.length() > 5 & stringBuilder.length() < 15) {
            manicureWeeksData.remove(stringBuilder.toString());
            stringBuilder.append(" и далее...");
            manicureWeeksData.put(stringBuilder.toString(), tempdate);
        }

        return manicureWeeksData;
    }

}
