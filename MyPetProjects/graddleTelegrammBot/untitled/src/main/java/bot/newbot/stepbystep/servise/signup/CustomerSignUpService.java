package bot.newbot.stepbystep.servise.signup;

import bot.newbot.data.DataManager;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record CustomerSignUpService(DataManager dataManager) implements SignUpService {

    @Override
    public BotApiMethod<? extends Serializable> regChoice(Update update) {
        String callback;
        if (update.getCallbackQuery() != null) {
            callback = update.getCallbackQuery().getData().split("_")[1];
        } else {
            callback = "userData";
        }

        switch (callback) {
            case "start":
                return stepOneRegistrationOffer(update);
            case "yes":
            case "editData":
                return stepTwoPleaseEnterYoreData(update);
            case "userData":
                return stepTreeConfirmUserData(update);
            case "confirmData":
                return finalStepSignUp(update);


        }

        throw new RuntimeException("Unknown callback query: SignUpService has no one callback query switch choice");
    }

    private BotApiMethod<? extends Serializable> stepOneRegistrationOffer(Update update) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText new_message = new EditMessageText();
        String answer = "Для записи на маникюр Вам нужно пройти простую процедуру регистрации. Хотите сделать это?";

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText("Да");
        yesButton.setCallbackData("signUp_yes");
        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText("Возможно позже");
        noButton.setCallbackData("later");


        rowInline.add(yesButton);
        rowInline.add(noButton);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);
        return new_message;
    }

    private BotApiMethod<? extends Serializable> stepTwoPleaseEnterYoreData(Update update) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText message = new EditMessageText();
        String answer = "Пожалуйста введите через пробел ваше имя и, при желании, телефон. Ваш телефон необходим для связи с Вами на случай, " +
                "если по каким-либо причинам не получится связаться иным способом. " +
                "Мы гарантируем, что Ваш телефон не будет передан третьим лицам и/или использован для распространения рекламы" +
                "\n\n\n" +
                "Пример вводимых данных: \nСветлана +7888888888\n\n Напоминаем: телефон не обязателен.";

        dataManager.setSignUpAwaitUserDataInput(chat_id, true);

        message.setChatId(chat_id);
        message.setMessageId((int) message_id);
        message.setText(answer);
        return message;
    }


    private BotApiMethod<? extends Serializable> stepTreeConfirmUserData(Update update) {
        SendMessage message = new SendMessage();
        long chatId = update.getMessage().getChatId();
        String[] data = update.getMessage().getText().replaceAll("[\\s]{2,}", " ").split(" ");

        if (data.length == 1) {
            dataManager.signUpUserAddPersonalUserData(chatId, data[0], "-", "@" + update.getMessage().getChat().getUserName());
        } else {
            dataManager.signUpUserAddPersonalUserData(chatId, data[0], data[1], "@" + update.getMessage().getChat().getUserName());
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

    private BotApiMethod<? extends Serializable> finalStepSignUp(Update update) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        String answer;

        if (dataManager.addNewCustomer(chat_id)) {
            answer = "Спасибо за регистрацию";
        } else {
            answer = "К сожалению что-то пошло не так. Обратитесь пожалуйста напрямую к мастеру маникюра для регистрации и записи на услугу: @Mary_art5";
        }

        new_message.setText(answer);
        return new_message;
    }

}
