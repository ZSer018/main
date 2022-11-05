package bot.service.users.manicure;

import bot.service.ResponseService;
import bot.managers.KeyboardsManager;
import bot.simplemessage.SimpleEditMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class CallbackCancelReg extends ResponseService {

    private final String text;

    public CallbackCancelReg(String text) {
        this.text = text;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        long chatId = update.hasCallbackQuery()? update.getCallbackQuery().getMessage().getChatId(): update.getMessage().getChatId();
        dataManager.manicureCancelReg(chatId);
        dataManager.userSetDefault(chatId);
        return List.of(
                new SimpleEditMessage(text).getNewEditMessage(update),
                KeyboardsManager.getSignInKeyboard("\uD83E\uDD72", update)
        );
    }
}
