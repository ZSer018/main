package bot.simplemessage;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class SimpleEditMessage{

    private final String editMessageText;

    public SimpleEditMessage(String editMessageText) {
        this.editMessageText = editMessageText;
    }

    public EditMessageText getNewEditMessage(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        int messageId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getMessageId(): update.getMessage().getMessageId();

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText(editMessageText);
        return message;
    }
}
