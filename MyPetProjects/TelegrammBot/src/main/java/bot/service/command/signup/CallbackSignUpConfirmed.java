package bot.service.command.signup;

import bot.managers.KeyboardsManager;
import bot.simplemessage.SimpleSendMessage;
import bot.service.ResponseService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.List;

public class CallbackSignUpConfirmed extends ResponseService {

    private boolean completed = false;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        return List.of(
                userSignUp(update),
                KeyboardsManager.getSignInKeyboard("Теперь Вы можете записаться на маникюр на любое удобное Вам время!", update),
                completed?
                        new SimpleSendMessage("\uD83D\uDE0A",0).getNewMessage(update):
                        new SimpleSendMessage("\uD83D\uDE41",0).getNewMessage(update)

        );
    }

    private EditMessageText userSignUp(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        int messageId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getMessageId(): update.getMessage().getMessageId();

        var customer = dataManager.getCustomerObject(chatId);

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);

        customer.setTelegramId(chatId);
        String answer = "null";
        if (dataManager.addNewCustomer(chatId)) {
            completed = true;
            answer = "Спасибо за регистрацию, " + customer.getName() + "!";
        } else {
            answer = "К сожалению что-то пошло не так. Обратитесь пожалуйста напрямую к мастеру маникюра для регистрации и записи на услугу: @Mary_art5";
        }
        message.setText(answer);

        return message;
    }


}
