package bot.service.users.filter;

import bot.managers.DataManager;
import bot.service.ResponseService;
import bot.service.users.filter.userStatusFilter.NoSignUpFilter;
import bot.service.users.filter.userStatusFilter.SignInFilter;
import bot.service.users.filter.userStatusFilter.SignUpFilter;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class TextFilter extends ResponseService {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        DataManager.userSignUpStatus userSignUpStatus = dataManager.getUserSignUpStatus(update.getMessage().getChatId());

        switch (userSignUpStatus) {

            case NO_SIGNUP: {
                return new NoSignUpFilter().responseAction(update);
            }

            case SIGNUP_COMPLETED: {
                return new SignInFilter().responseAction(update);
            }

            case SIGNUP_IN_PROGRESS: {
                return new SignUpFilter().responseAction(update);
            }

            default: {
                return List.of(new SimpleSendMessage("CustomerFilter: " + update.getMessage().getText() + "?").getNewMessage(update));
            }
        }
    }

}

