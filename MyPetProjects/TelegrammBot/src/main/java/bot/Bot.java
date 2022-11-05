package bot;

import bot.service.users.filter.CommandTypeFilter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_NAME;

    private final Object lock;


    public Bot(String BOT_TOKEN, String BOT_NAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_NAME = BOT_NAME;
        this.lock = new Object();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    //TODO вопрос всем перед приемом, придете ли вы
    //TODO удаление регистраций после часа приема
    //TODO логирование


    @Override
    public void onUpdateReceived(Update update) {
        synchronized (lock) {
                executeMessage(new CommandTypeFilter().responseAction(update));
        }
    }

    public void executeMessage(List<PartialBotApiMethod<? extends Serializable>> messages) {
        messages.forEach(message -> {
            String x = message.getClass().getName().substring(message.getClass().getName().lastIndexOf(".") + 1);
            try {
                switch (x) {
                    case "SendPhoto":
                        execute((SendPhoto) message);
                        break;
                    case "SendMediaGroup":
                        execute((SendMediaGroup) message);
                        break;
                    case "SendMessage":
                        execute((SendMessage) message);
                        break;
                    case "EditMessageText":
                        execute((EditMessageText) message);
                        break;
                }
            } catch (TelegramApiException e) {
                throw new IllegalStateException(e);
            }
        });
    }

   /*public void initSendPhoto(Update update) throws TelegramApiException {
       long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() : update.getMessage().getChatId();

       SendMediaGroup sendMediaGroup = new SendMediaGroup();
       List<InputMedia> inputMedia = new ArrayList<>();

       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIWbGNgt5MVl063XHEn5ELLAxbCPqsXAAKrwTEb73cIS9yc2QMbCZtrAQADAgADeAADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXa2NhSKng3db-Jg3_9O54-9hcKM_xAAL4wDEbo2QIS2oBM3aB10TWAQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXbWNhSLWUK9M8Agq4Iw_9FrEJhSpNAAL7wDEbo2QIS1dHNPX-492MAQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXb2NhSL2Xdn8ofUhSFQQrwFl2I1X1AAL8wDEbo2QIS_jWXWtkRneXAQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXcWNhSMXq8bSja2TgLcGTPDm5FZ5wAAL9wDEbo2QIS_XUL3fZSY24AQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXc2NhSM3VFEXoQmEqncrfTlJi1PB5AAL-wDEbo2QISzU_i2uKyu0SAQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIXdWNhSNdOITgwqJb17tF__oMXJ8mcAAL_wDEbo2QISy-yCtY_t-9HAQADAgADeQADKgQ"));
       inputMedia.add(new InputMediaPhoto("AgACAgIAAxkBAAIWbGNgt5MVl063XHEn5ELLAxbCPqsXAAKrwTEb73cIS9yc2QMbCZtrAQADAgADeAADKgQ"));

       sendMediaGroup.setMedias(inputMedia);
       sendMediaGroup.setChatId(chatId);
       execute(sendMediaGroup);
   }

    public SendMessage getNewMessage(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("KOKOKO");
        return message;
    }

    public EditMessageText getNewEditMessage(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        int messageId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getMessageId(): update.getMessage().getMessageId();

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText("LALALA");
        return message;
    }
*/





}