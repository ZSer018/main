package bot.simplemessage;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public class SimpleSendMessage {

    private final String messageText;

    public SimpleSendMessage(String messageText) {
        this.messageText = messageText;
    }

    public SendMessage getNewMessage(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }
}
