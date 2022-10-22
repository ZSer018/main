package bot.newbot.stepbystep.servise;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartupMenuActionService {
    public BotApiMethod<? extends Serializable> showMainMenu(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Здравствуйте, что бы Вы хотели?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton showPortfolioButton = new InlineKeyboardButton();
        showPortfolioButton.setText("Посмотреть портфолио");
        showPortfolioButton.setCallbackData("showPortfolio");
        rowInline1.add(showPortfolioButton);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton regForManicureButton = new InlineKeyboardButton();
        regForManicureButton.setText("Записаться на маникюр");
        regForManicureButton.setCallbackData("beautyServiceReg_start");
        rowInline2.add(regForManicureButton);

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton callMaryButton = new InlineKeyboardButton();
        callMaryButton.setText("Связь с мастером");
        callMaryButton.setCallbackData("callMary");
        rowInline3.add(callMaryButton);

        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
        InlineKeyboardButton laterButton = new InlineKeyboardButton();
        laterButton.setText("Возможно, позже...");
        laterButton.setCallbackData("later");
        rowInline4.add(laterButton);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }
}
