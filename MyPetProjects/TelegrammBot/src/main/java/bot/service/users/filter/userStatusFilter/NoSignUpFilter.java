package bot.service.users.filter.userStatusFilter;

import bot.managers.KeyboardsManager;
import bot.service.ResponseService;
import bot.service.users.call_mary.CallMary;
import bot.service.users.portfolio.ShowPortfolio;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class NoSignUpFilter extends ResponseService {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        long userId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        switch (text) {
            case "/start": {
                if (!dataManager.userIsView(userId)) {
                    dataManager.userSetDefault(userId);
                    return List.of(KeyboardsManager.getUserKeyboard("Здравствуйте! Что бы Вы хотели?", update));
                } break;
            }

            case "Зарегистрироваться": {
                if (!dataManager.userIsView(userId)) {
                    dataManager.signUpStart(userId);
                    return List.of(KeyboardsManager.getSignCancelKeyboard(null, update));
                } break;
            }

            case "Написать мастеру": {
                if (!dataManager.userIsView(userId)) {
                    return new CallMary().responseAction(update);
                } break;
            }







            case "Посмотреть портфолио": {
                if (dataManager.getServices().size() != 0) {
                    dataManager.userGoView(userId);
                    return List.of(KeyboardsManager.choosePhotoTypeKeyboard("Выберите пожалуйста раздел портфолио мастера", update));
                }else {
                    return List.of(new SimpleSendMessage("К сожалению данный раздел пока еще в разработке :(").getNewMessage(update));
                }
            }

            case "Закончить": {
                if (dataManager.userActivityStatus(userId)) {
                    dataManager.userSetDefault(userId);
                    return List.of(KeyboardsManager.getUserKeyboard("Здравствуйте! Что бы Вы хотели?", update));
                } break;
            }






            default: {
                if (dataManager.userIsView(userId) && dataManager.getServicesByName().contains(text)) {
                    dataManager.setUserViewType(userId, text);
                    return new ShowPortfolio(null).responseAction(update);
                }

                dataManager.userSetDefault(userId);
                return List.of(
                        new SimpleSendMessage("Я не понимаю эту команду : " + update.getMessage().getText() ).getNewMessage(update),
                        KeyboardsManager.getUserKeyboard("Давайте начнем заново :) ", update)
                );
            }

        }

        return List.of(new SimpleSendMessage("NoSignUpFilter: " + update.getMessage().getText() + "?").getNewMessage(update));
    }




}
