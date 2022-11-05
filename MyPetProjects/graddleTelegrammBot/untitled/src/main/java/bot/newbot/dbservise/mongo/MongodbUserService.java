package bot.newbot.dbservise.mongo;
import bot.newbot.dbservise.DBService;
import bot.newbot.objects.SignUpObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MongodbUserService implements DBService {

    private final MongoClientURI connectionString;

    public MongodbUserService() {
        connectionString = new MongoClientURI("mongodb://localhost:27017");
        //dropDB();
    }


    @Override
    public Map<Long, HashMap<String, String>> getCustomers() {
        var usermap = new HashMap<Long, HashMap<String, String>>();

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("BeautyService");
            MongoCollection<Document> collection = database.getCollection("users");

            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    var doc = cur.next();
                    var values = new HashMap<String, String>();
                    values.put("name", String.valueOf(doc.get("name")));
                    values.put("phone", String.valueOf(doc.get("phone")));
                    values.put("contact", String.valueOf(doc.get("contact")));

                    usermap.put( (long)doc.get("telegramId"), values);
                }
            }
            //usermap.forEach((aLong, strings) -> System.out.println(aLong+": "+strings.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            assert mongoClient != null;
            mongoClient.close();
        }
        return usermap;
    }

    @Override
    public void signUpCustomer(SignUpObject signUpObject) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("BeautyService");
            MongoCollection<Document> collection = database.getCollection("users");

            Document doc = new Document("telegramId", signUpObject.getTelegramId())
                    .append("name", signUpObject.getName())
                    .append("phone", signUpObject.getPhone())
                    .append("contact", signUpObject.getTgUsername());

            collection.insertOne(doc);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            assert mongoClient != null;
            mongoClient.close();
        }
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
        var regCalendarMap = new LinkedHashMap<String, LinkedHashMap<String, String>>(30);

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("BeautyService");
            MongoCollection<Document> collection = database.getCollection("regCalendar");

            Date today = Calendar.getInstance().getTime();

            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                   var doc = cur.next();

                    Date d = (Date) doc.get("date");
                    String outputDate = new SimpleDateFormat("yyyy.MM.dd").format(d);

                    if (today.compareTo(d) <= 0){
                        var temp = regCalendarMap.get(outputDate);
                        if (temp == null) {
                            temp = new LinkedHashMap<String, String>();
                            temp.put(String.valueOf(doc.get("time")), String.valueOf(doc.get("active")));
                        }
                        if (regCalendarMap.get(outputDate) != null) {
                            temp.put(String.valueOf(doc.get("time")), String.valueOf(doc.get("active")));
                        }

                        regCalendarMap.put(outputDate, temp);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            assert mongoClient != null;
            mongoClient.close();
        }

        if (regCalendarMap.size() < 30){
            serviceCalendarExtension(regCalendarMap);
        }
        //regCalendarMap.keySet().forEach(System.out::println);
        return regCalendarMap;
    }

    @Override
    public void serviceCalendarExtension(Map<String, LinkedHashMap<String, String>> regCalendarMap){
        Calendar calendar = Calendar.getInstance();

        if (regCalendarMap.size() > 0) {
            Date mdate = null;

            for (var temp: regCalendarMap.keySet() ) {
                try {
                    mdate = new SimpleDateFormat("yyyy.MM.dd").parse(temp);
                    calendar.setTime(mdate);
                } catch (ParseException e) {
                }
            }
        }


        MongoClient mongoClient = null;

        try {
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("BeautyService");
            MongoCollection<Document> collection = database.getCollection("regCalendar");
            ArrayList<Document> regDateDocuments = new ArrayList<>(30);
            String[] regTime = new String[]{"9:00", "12:00", "15:00", "18:00"};

            for (int i = 0; i < 10; i++) {
                calendar.add(Calendar.DATE, 1);
                Date d = calendar.getTime();
                var tempMap = new LinkedHashMap<String, String>();
                for (String s : regTime) {
                    Document doc = new Document("date", d)
                            .append("time", s)
                            .append("active", "+");
                    regDateDocuments.add(doc);
                    tempMap.put(s, "+");
                }

                String outputDate = new SimpleDateFormat("yyyy.MM.dd").format(d);
                regCalendarMap.put(outputDate, tempMap);
            }
            collection.insertMany(regDateDocuments);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            assert mongoClient != null;
            mongoClient.close();
        }
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
        MongoClient mongoClient = null;
        MongoCollection<Document> collection = null;
        try {
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("BeautyService");

            collection = database.getCollection("users");
            if (collection.countDocuments() != 0) {
                collection.drop();
            }
            collection = database.getCollection("users");
            if (collection.countDocuments() != 0) {
                collection.drop();
            }
            collection = database.getCollection("regCalendar");
            if (collection.countDocuments() != 0) {
                collection.drop();
            }
/*            collection = database.getCollection("users");
            if (collection.countDocuments() != 0) {
                collection.drop();
            }*/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assert mongoClient != null;
            mongoClient.close();
        }
    }

    @Override
    public void createDB() {

    }

}



