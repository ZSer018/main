package bot.newbot;

import bot.newbot.main.servises.CallbackQueryActionService;
import bot.newbot.main.servises.MessageTextActionService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;


public class Bot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_NAME;

    private final MessageTextActionService textActionService;
    private final CallbackQueryActionService callbackActionService;

    public Bot(String BOT_TOKEN, String BOT_NAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_NAME = BOT_NAME;

        textActionService = new MessageTextActionService();
        callbackActionService = new CallbackQueryActionService();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

/*    @Override
    public void onUpdatesReceived(List<Update> updatelist){
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        updatelist.forEach(out::println);
    }*/


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            executeAction(textActionService.responseAction(update));

        } else if (update.hasCallbackQuery()) {
            executeAction(callbackActionService.responseAction(update));
        }
    }

    private void executeAction(BotApiMethod<? extends Serializable> message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}