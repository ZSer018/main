package bot.newbot.main.servises;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public class CallbackQueryActionService extends ActionService{

    @Override
    public BotApiMethod<? extends Serializable> responseAction(Update update) {
        synchronized (lock) {
            long userId = update.getCallbackQuery().getFrom().getId();
            SendMessage message = new SendMessage();
            message.setChatId(userId);
            message.setText("Не реализовано");

            System.out.println(update.getCallbackQuery().getData());


            if (update.getCallbackQuery().getData().equals("showPortfolio")) {
                return message;
            }
            if (update.getCallbackQuery().getData().equals("callMary")) {
                return message;
            }
            if (update.getCallbackQuery().getData().equals("later")) {
                return message;
            }
            if (update.getCallbackQuery().getData().startsWith("beautyServiceReg")) {
                if (dataManager.userPresent(userId)) {
                    return manicureSignUpService.regChoice(update);
                } else {
                    dataManager.computeSignUpDataIfAbsent(userId);
                    return customerSignUpService.regChoice(update);
                }
            }
            if (update.getCallbackQuery().getData().startsWith("signUp")) {
                return customerSignUpService.regChoice(update);
            }
            return message;
        }
    }




}
