package bot.service.command.messagetoall;

import bot.service.ResponseService;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MessageToAll extends ResponseService {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        String text = update.getMessage().getText();

        var messages = new LinkedList<PartialBotApiMethod<? extends Serializable>>();
        long adminId = dataManager.getAdmin().getTelegramId();

        dataManager.getCustomers().forEach(customer -> {
            if (customer.getTelegramId() != adminId) {
                messages.add(new SimpleSendMessage(customer.getName() + "!\nХотим сообщить что:\n" + text, customer.getTelegramId()).getNewMessage(update));
            }
        });

        return messages;
    }
}
