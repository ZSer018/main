package bot;

import bot.managers.DataManager;
import bot.service.database.DBService;
import bot.service.database.mongo.MongodbService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

/*    private static String strDateToDateAndMonthName(String date){
        String temp[] = date.split("\\.");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]));

        return temp[2] + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru"));
    }*/
    //private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
/*        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 20; i++) {
            calendar.add(calendar.DATE, 1);
            Date d = calendar.getTime();
            String outputDate = new SimpleDateFormat("yyyy.MM.dd").format(d);
            System.out.println(outputDate);
            //System.out.println(calendar.get(Calendar.MONTH) + ", " + strDateToDateAndMonthName(outputDate));
        }*/


        logger.info("!!!!!!!!!!!!!!!!------------- Запуск приложения -------------!!!!!!!!!!!!!!!");
        //logger.warn("Something to warn");
        //logger.error("Something failed.");

        DBService dbService = new MongodbService();
        Bot bot = new Bot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot");
        DataManager.init(dbService, bot);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}