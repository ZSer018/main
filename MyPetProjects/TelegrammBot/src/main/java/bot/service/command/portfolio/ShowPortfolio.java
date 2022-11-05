package bot.service.command.portfolio;

import bot.managers.KeyboardsManager;
import bot.service.ResponseService;
import bot.simplemessage.SimpleEditMessage;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowPortfolio extends ResponseService {

    private List<String> viewingList = null;
    private boolean edited = false;

    public ShowPortfolio(List<String> viewingList) {
        this.viewingList = viewingList;
        if (viewingList != null) edited = true;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() : update.getMessage().getChatId();

        if (viewingList == null) {
            viewingList = dataManager.getPortfolioImgByType(chatId, dataManager.getUserViewType(chatId));
        }
        if (viewingList.size() == 0) {
            return List.of(new SimpleSendMessage("К сожалению в данном разделе пока нет ни одной фотографии",0).getNewMessage(update));
        }

        if (viewingList.size() == 1){
            return List.of(sendPic(chatId, viewingList.get(0), "Пока это единственная фотография в разделе"));
        }

        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);

        var result = new ArrayList<PartialBotApiMethod<? extends Serializable>>();
        var images = new ArrayList<InputMedia>();

        for (int i = 0; i < 10; i++) {
            if (viewingList.size() > 0){
                images.add(new InputMediaPhoto(viewingList.get(0)));
                viewingList.remove(0);
            } else break;
        }

        sendMediaGroup.setMedias(images);

        if (edited){
            if (viewingList.size() != 0) {
                result.add(new SimpleEditMessage("Далее >>").getNewEditMessage(update));
            } else
                result.add(new SimpleEditMessage("Последние работы").getNewEditMessage(update));
        }

        result.add(sendMediaGroup);

        if (images.size() == 10 & viewingList.size()>0)  {
            result.add(nextPartOfferMessage(chatId));
        }

        return result;
    }

    private SendPhoto sendPic(long chat_id, String fileId, String caption){
        SendPhoto picMsg = new SendPhoto();
        picMsg.setChatId(chat_id);
        picMsg.setPhoto(new InputFile(fileId));
        picMsg.setCaption(caption);
        return picMsg;
    }

    private SendMessage nextPartOfferMessage(long chatId) {
        SendMessage message = new SendMessage();
        String answer = "В этом разделе есть еще изображения";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(KeyboardsManager.getInlineKeyboardButton("Показать еще...", "viewing_ShowMore"));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(KeyboardsManager.getInlineKeyboardButton("Возможно позже", "viewing_later"));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        message.setChatId(chatId);
        message.setText(answer);
        return message;
    }

}



