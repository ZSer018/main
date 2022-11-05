package bot.newbot.main.servises;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextCommandService {

    public BotApiMethod<? extends Serializable> helloKeyboard(String messageText, long chatId){
        SendMessage message = new SendMessage();
        message.setText(messageText);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Посмотреть портфолио");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("Зарегистрироваться");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("Написать мастеру");
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        message.setChatId(chatId);

        return message;
    }

    public BotApiMethod<? extends Serializable> signInKeyboard(String messageText, long chatId){
        SendMessage message = new SendMessage();
        message.setText(messageText);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Отказаться от регистрации");
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Share your number >");
        keyboardButton.setRequestContact(true);
        row.add(keyboardButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        message.setChatId(chatId);

        return message;
    }

  /*  public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (message_text.equals("/start")) {
                try {
                    execute(MessageService.initSendMessage(message_text, chat_id, null,null)); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/pic")) {
                try {
                    execute(MessageService.initSendPhoto(chat_id, "AgACAgIAAxkBAAN8Y0zlWQHDXAFbJNnRMXbTEgJwERYAAnu_MRssd2FKXkTsZ-ydbtcBAAMCAAN5AAMqBA", "Caption"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/show")) {
                // Create ReplyKeyboardMarkup object
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                // Create the keyboard (list of keyboard rows)
                List<KeyboardRow> keyboard = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow row = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                row.add("Row 1 Button 1");
                row.add("Row 1 Button 2");
                row.add("Row 1 Button 3");
                // Add the first row to the keyboard
                keyboard.add(row);
                // Create another keyboard row
                row = new KeyboardRow();
                // Set each button for the second line
                row.add("Row 2 Button 1");
                row.add("Row 2 Button 2");
                row.add("Row 2 Button 3");
                // Add the second row to the keyboard
                keyboard.add(row);
                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboard);
                try {
                    execute(MessageService.initSendMessage("Here is your keyboard", chat_id, keyboardMarkup, null));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Row 1 Button 1")) {
                try {
                    execute(MessageService.initSendPhoto(chat_id, "AgACAgIAAxkBAAOfY0zo_eMbW-RuUZfK5l9VAw_sjXoAAnu_MRssd2FKXkTsZ-ydbtcBAAMCAANtAAMqBA", "Caption"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/hide")) {
                try {
                    execute(MessageService.initSendMessage("Keyboard hidden", chat_id, null, new ReplyKeyboardRemove()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(MessageService.initSendMessage("Unknown command", chat_id, null, null)); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Message contains photo
            // Set variables
            long chat_id = update.getMessage().getChatId();


            // КАЖДОЕ ПОСЛАННОЕ ФОТО ВОЗВРАЩАЕТСЯ В ТРЕХ РАЗМЕРАХ. Каждое сообщение содержит ID на каждый новый размер фото
            // Надо парсить
            List<PhotoSize> photos = update.getMessage().getPhoto();
            System.out.println(update.getMessage().getPhoto());

            System.out.println("-------------------------------------------------");
            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();
            int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();
            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
            try {
                execute(MessageService.initSendPhoto(chat_id, f_id, caption));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }*/

}
