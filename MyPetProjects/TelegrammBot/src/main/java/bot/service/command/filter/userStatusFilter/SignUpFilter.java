package bot.service.command.filter.userStatusFilter;

import bot.managers.KeyboardsManager;
import bot.service.ResponseService;
import bot.service.command.signup.CallbackPleaseConfirmData;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class SignUpFilter extends ResponseService {



    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        String text = update.getMessage().getText();

        System.out.println("---------------- "+text);

        var unusualCommands = List.of(
                "Написать мастеру",
                "Зарегистрироваться",
                "/start",
                "Посмотреть портфолио",
                "Закончить",
                "Отменить запись",
                "Записаться на маникюр",
                "Напомнить время записи");


        if (text.equals("Отказаться от регистрации")) {
            dataManager.cancelSignUp(update.getMessage().getChatId());
            return List.of(KeyboardsManager.getStartKeyboard("Регистрация отменена", update));
        }

        if (!unusualCommands.contains(text)) {
            return new CallbackPleaseConfirmData().responseAction(update);
        }

        return List.of(new SimpleSendMessage("В процессе регистрации данная команда недоступна. Закончите пожалуйста резистрацию, или откажитесь от нее.",0).getNewMessage(update));

    }


}
