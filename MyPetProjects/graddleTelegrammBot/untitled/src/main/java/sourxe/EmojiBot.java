package sourxe;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class EmojiBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_NAME;

    public EmojiBot(String BOT_TOKEN, String BOT_NAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_NAME = BOT_NAME;
    }


    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            long chat_id = update.getMessage().getChatId();
            // String answer = EmojiParser.parseToUnicode("\uD83D\uDE06\n");
            try {
                execute(MessageService.initSendMessage("\uD83D\uDE33", chat_id, null, null)); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }



}
