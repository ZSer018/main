package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.locks.ReentrantLock;


public class MessageService {

    private static final ReentrantLock lock = new ReentrantLock();

    public static SendMessage initSendMessage(String messageText, long chatId, ReplyKeyboardMarkup replyKeyboardMarkup, ReplyKeyboardRemove replyKeyboardRemove){
        lock.lock();
        SendMessage message = new SendMessage();
        message.setText(messageText);
        message.setChatId(chatId);
        if (replyKeyboardMarkup != null){
            message.setReplyMarkup(replyKeyboardMarkup);
        } else if (replyKeyboardRemove != null) {
            replyKeyboardRemove.setRemoveKeyboard(true);
            message.setReplyMarkup(replyKeyboardRemove);
        }
        lock.unlock();
        return message;
    }

    public static SendPhoto initSendPhoto(long chatId, String photoId, String photoCaption){
        lock.lock();
        SendPhoto picMsg = new SendPhoto();

        picMsg.setChatId(chatId);
        picMsg.setPhoto(new InputFile(photoId));
        picMsg.setCaption(photoCaption);

        lock.unlock();
        return picMsg;
    }
}
