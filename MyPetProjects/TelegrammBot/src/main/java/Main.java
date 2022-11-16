import bot.Bot;
import bot.managers.DataManager;
import bot.service.database.DBService;
import bot.service.database.mongo.MongodbService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.*;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main{

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args)  {
        logger.info("!!!!!!!!!!!!!!!!------------- Запуск приложения -------------!!!!!!!!!!!!!!!");


        String filePath = Bot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        filePath = new File(filePath).getParent() +"\\config\\bot.properties" ;
        long adminId = 0;
        String botToken = null;
        String botName = null;
        String adminName = null;
        String adminTGName = null;

        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filePath));
            adminId =  Long.parseLong(props.getProperty("ADMIN_ID", "1"));
            adminName = props.getProperty("ADMIN_NAME", "nonane");
            adminTGName = props.getProperty("ADMIN_TGUSERNAME", "nousername");
            botToken = props.getProperty("BOT_TOKEN", "5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM");
            botName = props.getProperty("BOT_NAME", "@Rikotta_bot");
        } catch (FileNotFoundException ignored) {
            adminId = 851450867L;
            //ADMIN_ID = 674694300L;
            botToken = "5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM";
            botName = "@Rikotta_bot";
            adminName = "nonane";
            adminTGName ="nousername";
        } catch (IOException ignored) {
        }


        DBService dbService = new MongodbService();
        Bot bot = new Bot(botToken, botName);

        //Bot bot = new Bot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot");
        //Bot bot = new Bot("5736402771:AAG7z_FRTpOPG3G9Qef8zwymQVZoNjKJPEo", "@NailServiceManager_bot");
        DataManager.init(dbService, adminId, adminName, adminTGName, bot);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


}