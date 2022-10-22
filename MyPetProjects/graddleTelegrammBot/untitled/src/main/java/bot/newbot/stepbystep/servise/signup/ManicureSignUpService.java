
package bot.newbot.stepbystep.servise.signup;

import bot.newbot.data.DataManager;
import bot.newbot.objects.ManicureRegObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record ManicureSignUpService(DataManager dataManager) implements SignUpService {

    @Override
    public BotApiMethod<? extends Serializable> regChoice(Update update) {
        long customerId = update.getCallbackQuery().getMessage().getChatId();

        System.out.println(update.getCallbackQuery().getData());

        int progressStep = dataManager().getManicureRegistrationProgress(customerId);

        switch (progressStep) {
            case 1:
                return firstStepSelectType(update);
            case 2:
                return regStepOneSelectWeek(update);
           case 3:
                return regStepTwoSelectDay(update);
             case 4:
                return regStepThreeSelectHour(update);
            case 5:
                return regFinalStep(update);
        }
        throw new RuntimeException("Unknown callback query: ManicureRegService has no one callback query switch choice");
    }


    private EditMessageText firstStepSelectType(Update update) {
        //System.out.println("---------1---------");
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        var manicureObject = dataManager.manicureRegistrationGetObject(chat_id);
        System.out.println(manicureObject.getStep());
        System.out.println("---------1--------- : "+update.getCallbackQuery().getData());

        EditMessageText new_message = new EditMessageText();
        String answer = "Какая услуга Вас интересует? ";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton typeNoCoverButton = new InlineKeyboardButton();
        typeNoCoverButton.setText("Маникюр без покрытия");
        typeNoCoverButton.setCallbackData("beautyServiceReg_typeNoCover");
        rowInline.add(typeNoCoverButton);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton typeWithCoverButton = new InlineKeyboardButton();
        typeWithCoverButton.setText("Маникюр с покрытием");
        typeWithCoverButton.setCallbackData("beautyServiceReg_typeWithCover");
        rowInline2.add(typeWithCoverButton);

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton typeExtensionButton = new InlineKeyboardButton();
        typeExtensionButton.setText("Наращивание");
        typeExtensionButton.setCallbackData("beautyServiceReg_typeExtension");
        rowInline3.add(typeExtensionButton);

        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        HashMap<String, String> types = new HashMap<>();
        types.put("beautyServiceReg_typeNoCover", "Маникюр без покрытия");
        types.put("beautyServiceReg_typeWithCover", "Маникюр с покрытием");
        types.put("beautyServiceReg_typeExtension", "Наращивание");
        manicureObject.setRegTypes(types);
        manicureObject.setStep(manicureObject.getStep() +1);


        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);
        return new_message;
    }

    private EditMessageText regStepOneSelectWeek(Update update) {
        //System.out.println("----------2---------");
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        System.out.println("----------2---------:"+update.getCallbackQuery().getData());

        EditMessageText new_message = new EditMessageText();
        String answer = "Выберите пожалуйста интересующие Вас числа";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline;
        InlineKeyboardButton keyboardButton;

        var manicureObject = dataManager.manicureRegistrationGetObject(chat_id);
        var temp = dataManager.getRegWeeks();
        for (var s : temp.entrySet()) {
            rowInline = new ArrayList<>();

            keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(s.getKey());
            keyboardButton.setCallbackData("beautyServiceReg_"+s.getValue());

            rowInline.add(keyboardButton);
            rowsInline.add(rowInline);
        }

        manicureObject.setChosenType(update.getCallbackQuery().getData().replaceAll("beautyServiceReg_", ""));
        manicureObject.setWeeks(temp);
        manicureObject.setStep(manicureObject.getStep() +1);

        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);
        return new_message;
    }

    private EditMessageText regStepTwoSelectDay(Update update) {
        //System.out.println("----------3---------");
        System.out.println("----------3---------:"+update.getCallbackQuery().getData());

        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText new_message = new EditMessageText();
        String answer = "Выберите пожалуйста интересующий Вас день";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline;
        InlineKeyboardButton keyboardButton;

        var manicureObject = dataManager.manicureRegistrationGetObject(chat_id);
        manicureObject.setChosenWeek(update.getCallbackQuery().getData().replaceAll("beautyServiceReg_", ""));

        var temp = dataManager.getRegDays(chat_id);
        for (var s : temp.entrySet()) {
            rowInline = new ArrayList<>();

            keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(s.getKey());
            keyboardButton.setCallbackData("beautyServiceReg_"+s.getKey());

            rowInline.add(keyboardButton);
            rowsInline.add(rowInline);
        }

        manicureObject.setDays(temp);
        manicureObject.setStep(manicureObject.getStep() +1);

        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);


        return new_message;
    }

    private EditMessageText regStepThreeSelectHour(Update update) {
        System.out.println("----------4---------:"+update.getCallbackQuery().getData());
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText new_message = new EditMessageText();
        String answer = "Выберите пожалуйста время";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline;
        InlineKeyboardButton keyboardButton;

        var manicureObject = dataManager.manicureRegistrationGetObject(chat_id);
        manicureObject.setChosenDay(update.getCallbackQuery().getData().replaceAll("beautyServiceReg_", ""));

        var temp = dataManager.getRegHours(chat_id);
        for (var s : temp) {
            rowInline = new ArrayList<>();

            keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(s);
            keyboardButton.setCallbackData("beautyServiceReg_"+s);

            rowInline.add(keyboardButton);
            rowsInline.add(rowInline);
        }

        manicureObject.setHours(temp);
        manicureObject.setStep(manicureObject.getStep() +1);

        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);


        return new_message;
    }

    private EditMessageText regFinalStep(Update update) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        var manicureObject = dataManager.manicureRegistrationGetObject(chat_id);
        manicureObject.setChosenHour(update.getCallbackQuery().getData().replaceAll("beautyServiceReg_", ""));
        manicureObject.setStep(-1);

        //Запись в базу
        EditMessageText new_message = new EditMessageText();
        String answer = "Вы записаны: ";

        new_message.setChatId(chat_id);
        new_message.setMessageId((int) message_id);
        new_message.setText(answer);
        return new_message;
    }


}
