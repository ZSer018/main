package bot.newbot.stepbystep.servise.signup;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public interface SignUpService {
    BotApiMethod<? extends Serializable> regChoice(Update update);
}
