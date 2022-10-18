package bot.telegramm;
import bot.InlineKeyboardBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            //TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            //botsApi.registerBot(new MessageBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
           //botsApi.registerBot(new PictureBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
            //botsApi.registerBot(new LoginBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
            //botsApi.registerBot(new EmojiBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
            botsApi.registerBot(new InlineKeyboardBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
            //botsApi.registerBot(new MongoDBBot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}