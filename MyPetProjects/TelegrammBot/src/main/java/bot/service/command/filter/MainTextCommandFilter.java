package bot.service.command.filter;

import bot.managers.DataManager;
import bot.service.ResponseService;
import bot.service.command.filter.userStatusFilter.NoSignUpFilter;
import bot.service.command.filter.userStatusFilter.SignInFilter;
import bot.service.command.filter.userStatusFilter.SignUpFilter;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class MainTextCommandFilter extends ResponseService {

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
                return List.of(new SimpleSendMessage(update.getMessage().getText() + "?",0).getNewMessage(update));
            }
        }
    }

}

