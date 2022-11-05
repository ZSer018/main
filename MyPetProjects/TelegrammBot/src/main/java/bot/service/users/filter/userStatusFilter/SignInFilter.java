package bot.service.users.filter.userStatusFilter;

import bot.managers.DataManager;
import bot.managers.KeyboardsManager;
import bot.service.ResponseService;
import bot.service.users.call_mary.CallMary;
import bot.service.users.filter.admin.AdminTextFilter;
import bot.service.users.manicure.CancelRegStart;
import bot.service.users.manicure.StartRegistration;
import bot.service.users.portfolio.ShowPortfolio;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class SignInFilter  extends ResponseService {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update){
        long userId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if (dataManager.getAdmin().getTelegramId() == userId){
            return new AdminTextFilter().responseAction(update);
        }

        if (dataManager.customerManicureRegStatus(userId) != DataManager.manicureRegStatus.NO_REG_ERROR &&
                dataManager.customerManicureRegStatus(userId) != DataManager.manicureRegStatus.REG_COMPLETE){
            return List.of(new SimpleSendMessage("Закончите пожалуйста регистрацию, или откажитесь от нее").getNewMessage(update));
        }


        switch (text){
            case "/start": {
                if (!dataManager.userIsView(userId)) {
                    dataManager.userSetDefault(userId);
                    return List.of(KeyboardsManager.getUserKeyboard("Здравствуйте, " + dataManager.getUserName(userId) + "! Что бы Вы хотели?", update));
                } break;
            }

            case "Напомнить время записи": {
                    return List.of( new SimpleSendMessage(dataManager.getUserManicureRegData(userId)).getNewMessage(update));
            }

            case "Записаться на маникюр": {
                if (dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.NO_REG_ERROR) {
                    return new StartRegistration().responseAction(update);
                }
            }

            case "Посмотреть портфолио": {
                if (dataManager.getServices().size() != 0) {
                    dataManager.userGoView(userId);
                    return List.of(KeyboardsManager.choosePhotoTypeKeyboard(dataManager.getUserName(userId)+ ", выберите пожалуйста раздел портфолио мастера", update));
                }else {
                    return List.of(new SimpleSendMessage("К сожалению данный раздел пока еще в разработке :(").getNewMessage(update));
                }
            }

            case "Написать мастеру": {
                return new CallMary().responseAction(update);
            }

            case "Отменить запись": {
                    return new CancelRegStart().responseAction(update);
            }

            case "Закончить" : {
                if (dataManager.userActivityStatus(userId)){
                    return List.of(KeyboardsManager.getUserKeyboard("Здравствуйте, " + dataManager.getUserName(userId) + "! Что бы Вы хотели?", update));
                }
            }


            default: {
                if (dataManager.userIsView(userId) && dataManager.getServicesByName().contains(text)) {
                    dataManager.setUserViewType(userId, text);
                    return new ShowPortfolio(null).responseAction(update);
                }

                dataManager.userSetDefault(userId);
                return List.of(
                        new SimpleSendMessage("SignInFilter: " + update.getMessage().getText() + "?").getNewMessage(update),
                        KeyboardsManager.getUserKeyboard("Здравствуйте, " + dataManager.getUserName(userId) + "! Что бы Вы хотели?", update)
                );
            }
        }

        return List.of(KeyboardsManager.getUserKeyboard("Здравствуйте, " + dataManager.getUserName(userId) + "! Что бы Вы хотели?", update));
    }
}