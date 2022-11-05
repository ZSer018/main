package bot.service.users.call_mary;

import bot.service.ResponseService;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class CallMary extends ResponseService {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        return List.of(new SimpleSendMessage("Вы можете связаться с мастером, кликнув по ее никнэйму \n>> "+dataManager.getAdmin().getTgUsername()+" <<" +
                "\nМастера зовут "+dataManager.getAdmin().getName()).getNewMessage(update));
    }
}
