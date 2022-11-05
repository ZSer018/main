package bot.service.admin;
import bot.service.ResponseService;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class AddDeletePhoto extends ResponseService {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        return List.of(savePhoto(update));
    }

    public SendMessage savePhoto(Update update){
        List<PhotoSize> photos = update.getMessage().getPhoto();
        String fileId = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();

        /*Удаление фотографий*/
        if (dataManager.getAdmin().isDeletePhotos()) {
            dataManager.removeImgFromPortfolio(fileId);
            return new SimpleSendMessage("Фотография удалена",0).getNewMessage(update);
        }


        /*Добавление фотографий*/
        if (!dataManager.getAdmin().getViewingType().equals("-none-")) {
            dataManager.addPortfolioImg(fileId);
            return new SimpleSendMessage("Фотография загружена", 0).getNewMessage(update);
        }

        return new SimpleSendMessage("Для загрузки/ удаления фотографий необходимо выбрать соответсвующий раздел портфолио",0 ).getNewMessage(update);
    }



}
