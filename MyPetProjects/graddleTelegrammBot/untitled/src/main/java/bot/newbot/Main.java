package bot.newbot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException {

/*        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = new Calendar.Builder().setDate(2020, 4, 5).build();
        for (int i = 0; i < 30; i++) {
            calendar.add(Calendar.DATE, 1);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, new Locale("ru"));
            //System.out.println(calendar.get(Calendar.DATE)+" "+month+" "+ calendar.get(Calendar.YEAR));
            System.out.println(calendar.get(Calendar.DATE)+"."+calendar.get(Calendar.MONTH)+"."+ calendar.get(Calendar.YEAR));
        }*/

       // System.out.println(calendar.get(Calendar.YEAR)+1 +"."+ calendar.get(Calendar.MONTH) +"."+ calendar.get(Calendar.DAY_OF_MONTH));

/*

        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("привет");
        System.out.println("привет");
*/


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("5691626921:AAHnkCqZolUvgx6zJifELpuuYDQHA5RRuKM", "@Rikotta_bot"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}