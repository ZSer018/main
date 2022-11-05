package bot.service.users.manicure;

import bot.managers.DataManager;
import bot.service.ResponseService;
import bot.managers.KeyboardsManager;
import bot.simplemessage.SimpleEditMessage;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CallbackRegComplete extends ResponseService {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        var temp = new ArrayList<PartialBotApiMethod<? extends Serializable>>();

        if (regFinalStep(update)){
           temp.add(new SimpleEditMessage("Вы успешно зарегистрированы!").getNewEditMessage(update));
            temp.add(KeyboardsManager.getSignInKeyboard("С нетерпением будем Вас ждать!", update));
           temp.add(new SimpleSendMessage("\uD83D\uDE0A").getNewMessage(update));
        } else {
            temp.add(new SimpleEditMessage("К сожалению что-то пошло не так. Пожалуйста обратитесь напрямую к мастеру маникюра для регистрации и записи на услугу: "+
                    dataManager.getAdmin().getTgUsername()).getNewEditMessage(update));
            temp.add(KeyboardsManager.getSignInKeyboard("Приносим свои извинения", update));
            temp.add(new SimpleSendMessage("\uD83D\uDE14").getNewMessage(update));
        }

        return temp;
    }

    private boolean regFinalStep(Update update) {
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        if (dataManager.customerManicureRegStatus(chat_id) != DataManager.manicureRegStatus.SELECT_HOUR){
            return false;
        }

        var manicureObject = dataManager.getManicureRegObject(chat_id);
        manicureObject.setTime(update.getCallbackQuery().getData().replaceAll("BSReg_hour_", ""));
        manicureObject.setTelegramId(chat_id);

        return dataManager.manicureDBRegistration(chat_id);
    }
}
