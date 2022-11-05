package sourxe;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static java.lang.Math.toIntExact;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MongoDBBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_NAME;

    private boolean isDropped = false;

        public MongoDBBot(String BOT_TOKEN, String BOT_NAME) {
            this.BOT_TOKEN = BOT_TOKEN;
            this.BOT_NAME = BOT_NAME;
        }

        @Override
        public void onUpdateReceived(Update update) {
            // We check if the update has a message and the message has text
            if (update.hasMessage() && update.getMessage().hasText()) {
                String user_first_name = update.getMessage().getChat().getFirstName();
                String user_last_name = update.getMessage().getChat().getLastName();
                String user_username = update.getMessage().getChat().getUserName();
                long user_id = update.getMessage().getChat().getId();
                long chat_id = update.getMessage().getChatId();
                System.out.println(user_id + ", "+ chat_id);


                check(user_first_name, user_last_name, (int)user_id, user_username);


                String message_text = update.getMessage().getText();

                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText(message_text);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


        private String check(String first_name, String last_name, int user_id, String username) {
            MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("users");
            MongoCollection<Document> collection = database.getCollection("tgBotUsers");

            if (!isDropped) {
                //collection.drop();
                isDropped = true;
            }
            long found = collection.countDocuments(Document.parse("{id : " + Integer.toString(user_id) + "}"));

            System.out.println(found);
            if (found == 0) {
                Document doc = new Document("first_name", first_name)
                        .append("last_name", last_name)
                        .append("id", user_id)
                        .append("username", username);
                collection.insertOne(doc);
                mongoClient.close();
                System.out.println("Пользователя не найдено. Заносим его в базу");
                return "no_exists";
            } else {
                System.out.println("Пользователь уже существует");
                mongoClient.close();
                return "exists";
            }


        }
}
