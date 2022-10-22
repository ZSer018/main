package bot.newbot.main.servises;

import bot.newbot.data.DataManager;
import bot.newbot.stepbystep.servise.signup.ManicureSignUpService;
import bot.newbot.stepbystep.servise.signup.CustomerSignUpService;
import bot.newbot.dbservise.DBService;
import bot.newbot.dbservise.mongo.MongodbUserService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public abstract class ActionService {
    protected static DataManager dataManager;
    protected static CustomerSignUpService customerSignUpService;
    protected static ManicureSignUpService manicureSignUpService;

    protected final Object lock = new Object();

    public ActionService() {
        if (dataManager == null) {
            DBService dbService = new MongodbUserService(); //TODO получать сервис из конфига

            dataManager = new DataManager(dbService);
            manicureSignUpService = new ManicureSignUpService(dataManager);
            customerSignUpService = new CustomerSignUpService(dataManager);
        }
    }

    public abstract BotApiMethod<? extends Serializable> responseAction(Update update);
}
