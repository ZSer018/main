package bot.threads.scheduler;

import bot.Bot;
import bot.managers.DataManager;
import bot.managers.KeyboardsManager;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotifyTask{

    private static boolean notified = false;

    public static void runIteration() {
        System.out.println(" NotifyTask START");

        Date date = new Date();
        String hour = new SimpleDateFormat("kk").format(date);

        if (hour.equals("14") && !notified) {
            final var notifyList = new ArrayList<PartialBotApiMethod<? extends Serializable>>();
            DataManager dataManager = DataManager.getInstance();

            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(tomorrow.DATE, 1);

            Date tomorrowDate = tomorrow.getTime();
            String tomorrowStr = new SimpleDateFormat("yyyy.MM.dd").format(tomorrowDate);

            dataManager.getManicureCustomers().forEach(customerRegData -> {
                if (customerRegData.getDate().equals(tomorrowStr)) {
                    notifyList.add(getNotifyMessage("Здравствуйте, " + dataManager.getUserName(customerRegData.getTelegramId())
                                    + "!\nНапоминаем, что завтра Вы записаны на прием к мастеру маникюра: \nВремя: "
                                    + customerRegData.getTime() + "\nУслуга: " + customerRegData.getManicureType() + "\nЦена: " + customerRegData.getCost() + "\nВы придете?"
                            , customerRegData.getTelegramId()));
                }
            });
            dataManager.transferMessages(notifyList);
            notified = true;
        } else if (!hour.equals("13")) {
            notified = false;
        }
    }

    private static SendMessage getNotifyMessage(String messageText, long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> btnInline;

        btnInline = new ArrayList<>();
        btnInline.add(KeyboardsManager.getInlineKeyboardButton("Да, я приду", "regConfirm_YES"));
        rows.add(btnInline);

        btnInline = new ArrayList<>();
        btnInline.add(KeyboardsManager.getInlineKeyboardButton("К сожалению нет", "regConfirm_NO"));
        rows.add(btnInline);

        markupInline.setKeyboard(rows);

        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.setReplyMarkup(markupInline);
        message.setText(messageText);
        return message;
    }

}
