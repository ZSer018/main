package bot.newbot.main.servises;

import bot.newbot.stepbystep.servise.StartupMenuActionService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;

public class MessageTextActionService extends ActionService{

    private final StartupMenuActionService startupMenu = new StartupMenuActionService();

    @Override
    public BotApiMethod<? extends Serializable> responseAction(Update update) {
        synchronized (lock) {
            long userId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(userId);

            if (update.getMessage().getText().equals("reg")) {
                return startupMenu.showMainMenu(userId);
            }

            if (dataManager.userReceivePersonalData(userId)) {
                return customerSignUpService.regChoice(update);
            }

            return message;
        }
    }


   /* public BotApiMethod<? extends Serializable> responseActon(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        if (update.getMessage().getText().equals("reg")) {
            message.setText("reg");
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText("Записаться на маникюр");
            keyboardButton.setCallbackData("beautyServiceReg");
            rowInline.add(keyboardButton);
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            return message;
        }

        if (dataManager.userReceivePersonalData(chatId)){
            String[] data = update.getMessage().getText().split(" ");
            if (data.length == 1) {
                dataManager.signUpUserAddPersonalUserData(chatId, data[0], "-", "@"+update.getMessage().getChat().getUserName());
            } else {
                dataManager.signUpUserAddPersonalUserData(chatId, data[0], data[1], "@"+update.getMessage().getChat().getUserName());
            }

            int x = update.getMessage().getText().split(" ").length;
            String answer = "Правильно ли введены Ваши данные? \n" + dataManager.getSignUpPersonalUserData(chatId);

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton okButton = new InlineKeyboardButton();
            okButton.setText("Да, все верно");
            okButton.setCallbackData("signUp_confirmData");
            InlineKeyboardButton editButton = new InlineKeyboardButton();
            editButton.setText("Изменить данные");
            editButton.setCallbackData("signUp_editData");
            rowInline.add(okButton);
            rowInline.add(editButton);

            List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
            InlineKeyboardButton laterButton = new InlineKeyboardButton();
            laterButton.setText("Отмена");
            laterButton.setCallbackData("signUp_checkData");
            rowInline2.add(laterButton);

            rowsInline.add(rowInline);
            rowsInline.add(rowInline2);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            message.setChatId(chatId);
            message.setText(answer);
            return message;
        }


        return message;
    }*/
}
