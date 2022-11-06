package bot.managers;
import bot.Bot;
import bot.objects.CustomerObject;
import bot.objects.ManicureRegObject;
import bot.service.database.DBService;
import bot.threads.scheduler.ManicureRegistrationMapUpdateTask;
import bot.threads.scheduler.NotifyTask;
import bot.threads.scheduler.RegCalendarUpdaterTask;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.K;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DataManager {

    private static DataManager dataManager;
    private final DBService dbService;
    private static ScheduledExecutorService executorService;
    private final Bot bot;



    private final CustomerObject ADMIN;
    private final Map<Long, CustomerObject> customers;
    private final Map<String, String> portfolio;
    private Map<String, LinkedHashMap<String, String>> manicureFreeDateCalendar;
    private final Map<String, Integer> servicesAndPrices;
    private Map<Long, ManicureRegObject> customersManicureRegistration;





    public enum userSignUpStatus { NO_SIGNUP, SIGNUP_COMPLETED, SIGNUP_IN_PROGRESS}
    public enum manicureRegStatus {NO_REG_ERROR, SELECT_TYPE, SELECT_WEEK, SELECT_DAY, SELECT_HOUR, REG_COMPLETE}



    private DataManager(DBService dbService, Bot bot) {
        this.dbService = dbService;
        this.bot = bot;
        executorService = Executors.newScheduledThreadPool(3);


        //dbService.dropDB();


        customers = dbService.getCustomers();
        ADMIN = new CustomerObject();
        ADMIN.setName("Мария");
        ADMIN.setTelegramId(851450867);
        //ADMIN.setTelegramId(674694300);
        ADMIN.setTgUsername("@Mary_art5");
        customers.put(ADMIN.getTelegramId(), ADMIN);


        portfolio = dbService.getPortfolio();
        manicureFreeDateCalendar = dbService.getServiceCalendar();
        servicesAndPrices = dbService.getServicesAndPrices();
        customersManicureRegistration = dbService.getCustomersManicureRegistration();
    }

    public static void init(DBService dbService, Bot bot){
        if (dataManager == null) {
            dataManager = new DataManager(dbService, bot);

            executorService.scheduleAtFixedRate(NotifyTask::runIteration, 0, 1, TimeUnit.HOURS);
            executorService.scheduleAtFixedRate(ManicureRegistrationMapUpdateTask::runIteration, 0, 1, TimeUnit.HOURS);
            executorService.scheduleAtFixedRate(RegCalendarUpdaterTask::runIteration, 0, 1, TimeUnit.DAYS);
        } else throw new IllegalStateException("Instance already was initialized");
    }

    public static DataManager getInstance() {
        if (dataManager != null) {
            return dataManager;
        }

        throw new IllegalStateException("Instance is not initialize");
    }

    public void transferMessages(List<PartialBotApiMethod<? extends Serializable>> messageList){
        bot.executeMessage(messageList);
    }





    public void addPortfolioImg(String imgId){
        dbService.addImageToPortfolio(dataManager.ADMIN.getViewingType(), imgId);
        portfolio.put(imgId, dataManager.ADMIN.getViewingType());
    }

    public void removeImgFromPortfolio(String imgId){
        dbService.removeImageFromPortfolio(imgId);
        portfolio.remove(imgId);
    }

    public List<String> getPortfolioImgByType(long userId, String type){
       customers.get(userId).setViewingImageList(portfolio.entrySet().stream()
                .filter(entry -> entry.getValue().equals(type))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));

       return customers.get(userId).getViewingImageList();
    }








    public String strDateToDateAndMonthName(String date){
        String temp[] = date.split("\\.");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]));
        return temp[2] + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru"));
    }

    public HashMap<String, String> getRegWeeks() {
        int x = -1;
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        String tempdate = null;

        LinkedHashMap<String, String> manicureWeeksData = new LinkedHashMap<>();
        for (var entry : manicureFreeDateCalendar.keySet()) {
            x++;
            if (x > 7) {
                x = 0;
            }

            if (x == 0) {
                tempdate = entry;
                stringBuilder.append(strDateToDateAndMonthName(entry));
            }
            if (x == 7) {
                stringBuilder.append("  -  ").append(strDateToDateAndMonthName(entry));
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

    public LinkedHashMap<String, String> getRegDays(long customerId) {
        var temp = new LinkedHashMap<String, String>();
        String date = customersManicureRegistration.get(customerId).getChosenWeek();

        int x = 0;
        boolean getSeven = false;
        for (var entry : manicureFreeDateCalendar.entrySet()) {
            if (entry.getKey().equals(date)) {
                getSeven = true;
            }
            if (getSeven) {
                temp.put(entry.getKey(), strDateToDateAndMonthName(entry.getKey()));
                x++;
            }
            if (x == 8) {
                break;
            }
        }

        return temp;
    }

    public ArrayList<String> getRegHours(long customerId) {
        var temp = new ArrayList<String>();
        String date = customersManicureRegistration.get(customerId).getDate();

        var hours = manicureFreeDateCalendar.get(date);
        if (hours.entrySet().stream().anyMatch(e -> e.getValue().equals("+"))) {
            hours.entrySet().stream().filter(e -> e.getValue().equals("+")).forEach(e -> temp.add(e.getKey()));

        }
        return temp;
    }







    public Collection<CustomerObject> getCustomers(){
        return customers.values();
    }

    public void usersContains(long userId){
        if (!customers.containsKey(userId)){
            customers.put(userId, new CustomerObject());
        }
    }

    public void userSetDefault(long userId) {
        CustomerObject customer = customers.get(userId);
        customer.setViewingPhotos(false);
        customer.setAddingPhotos(false);
        customer.setDeletePhotos(false);
        customer.setSendingMessagesToAllCustomers(false);
        customer.setViewingType("-none-");
        customer.setEditServicesAndPrices(false);
        customer.setViewingImageList(null);
    }

    public boolean userActivityStatus(long userId) {
        CustomerObject customer = customers.get(userId);
        return customer.isViewingPhotos() || customer.isAddingPhotos() || customer.isDeletePhotos() || customer.isEditServicesAndPrices();
    }

    public void userGoView(long userId) {
        customers.get(userId).setViewingPhotos(true);
    }

    public boolean userIsView(long userId) {
        if (customers.containsKey(userId)) {
            return customers.get(userId).isViewingPhotos();
        }

        return false;
    }

    public String getUserViewType(long userId) {
        return customers.get(userId).getViewingType();
    }

    public void setUserViewType(long userId, String text) {
        customers.get(userId).setViewingType(text);
    }

    public List<String> getUserViewingList(long userId) {
        return customers.get(userId).getViewingImageList();
    }

    public String getUserName(long userId) {
        return customers.get(userId).getName();
    }

    public void signUpStart(long userId) {
        customers.get(userId).setName("regStart");
    }

    public userSignUpStatus getUserSignUpStatus(long userId) {
        if (!customers.get(userId).equals(new CustomerObject())) {
            if (customers.get(userId).getTelegramId() != 0) {
                return userSignUpStatus.SIGNUP_COMPLETED;
            }
                return userSignUpStatus.SIGNUP_IN_PROGRESS;
        }
        return userSignUpStatus.NO_SIGNUP;
    }

    public CustomerObject getCustomerObject(long userId){
        return customers.get(userId);
    }

    public void cancelSignUp(long id){
        customers.put(id, new CustomerObject());
    }

    public boolean addNewCustomer(long userId) {
        try {
            dbService.signUpCustomer(customers.get(userId));
        } catch (Exception e) {
            System.out.println("Error DB insert: " + e.getMessage());
            return false;
        }
        return true;
    }









    public CustomerObject getAdmin(){
        return ADMIN;
    }







    public String getServicesAndPricesString(){
        if (servicesAndPrices.size() == 0){
            return "-Список услуг пуст-";
        }

        StringBuilder stringBuilder = new StringBuilder();
        servicesAndPrices.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append("\n"));
        return stringBuilder.toString();
    }

    public Set<String> getServices(){
        if (servicesAndPrices.size() != 0){
            return servicesAndPrices.keySet();
        }
        return new HashSet<>();
    }

    public void addOrUpdateServiceAndPrice(String serviceName, int price){
        String name = "-none-";
        for (var entry: servicesAndPrices.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(serviceName)){
                name = entry.getKey();
            }
        }

        if (!name.equals("-none-")) {
            dbService.addOrUpdateServiceAndPrice(name, price);
            servicesAndPrices.put(name, price);
        }else {
            dbService.addOrUpdateServiceAndPrice(serviceName, price);
            servicesAndPrices.put(serviceName, price);
        }
    }

    public boolean removeService(String serviceName){
        String name = null;
        for (var entry: servicesAndPrices.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(serviceName)){
                name = entry.getKey();
            }
        }

        if (name != null) {
            dbService.adminRemoveService(serviceName, servicesAndPrices.get(name));
            servicesAndPrices.remove(name);
            return true;
        }

        return false;
    }

    public int getServicePrice(String serviceName){
        return servicesAndPrices.get(serviceName);
    }

    public Set<String> getServicesByName(){
        return servicesAndPrices.keySet();
    }








    public ManicureRegObject getManicureRegObject(long userId) {
        return customersManicureRegistration.get(userId);
    }

    public void manicureRegStart(long userId){
        customersManicureRegistration.put(userId, new ManicureRegObject());
    }

    public void manicureCancelReg(long userId){
        updateRegCalendar(getManicureRegObject(userId), true);

        dbService.removeCustomerManicureRegistration(customersManicureRegistration.get(userId));
        customersManicureRegistration.remove(userId);
    }

    public manicureRegStatus customerManicureRegStatus(long userId){

        if (customersManicureRegistration.containsKey(userId)){
            ManicureRegObject reg = customersManicureRegistration.get(userId);

            if (reg.getTelegramId() != 0){
                return manicureRegStatus.REG_COMPLETE;
            } else if (reg.getDate() != null){
                return manicureRegStatus.SELECT_HOUR;
            }else if (reg.getChosenWeek() != null){
                return manicureRegStatus.SELECT_DAY;
            } else if (reg.getManicureType() != null){
                return manicureRegStatus.SELECT_WEEK;
            } else return manicureRegStatus.SELECT_TYPE;
        }
        return manicureRegStatus.NO_REG_ERROR;
    }

    public boolean manicureDBRegistration(long userId) {
        try {
            dbService.regCustomerForManicure(customersManicureRegistration.get(userId));
        } catch (Exception e) {
            System.out.println("Error DB insert: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Collection<ManicureRegObject> getManicureCustomers(){
        return customersManicureRegistration.values();
    }


    public String getUserManicureRegData(long userId){
        return "Дата : "+strDateToDateAndMonthName(customersManicureRegistration.get(userId).getDate()) +"\n"+
               "Время : "+customersManicureRegistration.get(userId).getTime() +"\n"+
               "Услуга : "+customersManicureRegistration.get(userId).getManicureType() +"\n"+
               "Стоимость : "+customersManicureRegistration.get(userId).getCost();
    }

    public String getAllManicureRecords(){
        StringBuilder regList = new StringBuilder();

        customersManicureRegistration.forEach((K, V) -> {
            regList.append(strDateToDateAndMonthName(V.getDate()))
                    .append(", ")
                    .append(V.getTime())
                    .append(": \n")
                    .append(customers.get(K).getName()).append(", \n")
                    .append(V.getManicureType())
                    .append(", цена: ")
                    .append(V.getCost())
                    .append("\n\n");

        });

        return regList.toString();
    }

    public String getTodayManicureRecords() {
        StringBuilder regList = new StringBuilder();

        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        String today = new SimpleDateFormat("yyyy.MM.dd").format(d);

        var list = customersManicureRegistration
                .values()
                .stream()
                .filter(v -> v.getDate().equals(today))
                .collect(Collectors.toList());


        if (list.size() != 0) {
            list.forEach(customer -> {
                regList .append(customer.getTime())
                        .append(": \n")
                        .append(customers.get(customer.getTelegramId()).getName()).append(", \n")
                        .append(customer.getManicureType())
                        .append(", цена: ")
                        .append(customer.getCost())
                        .append("\n\n");

            });
        }

        return regList.toString();
    }

    public String getFreeManicureDateTime(){
        StringBuilder regList = new StringBuilder();
        var freeReg = new LinkedHashMap<String, ArrayList<String>>();

        for (var entry : manicureFreeDateCalendar.entrySet() ) {

            var map =  entry.getValue();
            var list = new ArrayList<String>();
            for (var entry2: map.entrySet()) {
                System.out.println(entry2.getKey() + ": "+ entry2.getValue());
                if (entry2.getValue().equals("+")){
                    list.add(entry2.getKey());
                }
            }
            freeReg.put(entry.getKey(), list);
        }


        freeReg.forEach((date, hours) -> {
            regList.append(strDateToDateAndMonthName(date))
                    .append(": ")
                    .append(hours.size()==4? "  - весь день -  " : "  "+ Arrays.toString(hours.toArray()))
                    .append("\n");
        });

        return regList.toString();
    }


    public void updateRegCalendar(ManicureRegObject manicureRegObject, boolean canselReg){
        var temp = manicureFreeDateCalendar.get(manicureRegObject.getDate());
        if (temp != null) {
            if (canselReg) {
                temp.put(manicureRegObject.getTime(), "+");
            } else {
                temp.put(manicureRegObject.getTime(), "-");
            }
            manicureFreeDateCalendar.put(manicureRegObject.getDate(), temp);
        }
    }

    public void updateAndReloadFreeDateCalendar(){
        System.out.println("update reg calendar");
        manicureFreeDateCalendar = dbService.getServiceCalendar();
    }

    public void reloadCustomersManicureRegistration(){
        customersManicureRegistration = dbService.getCustomersManicureRegistration();
    }

}
