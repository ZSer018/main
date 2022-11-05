package bot.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CustomerObject {

    //IF THIS USER ADMIN
    private boolean addingPhotos = false;
    private boolean deletePhotos = false;
    private boolean editServicesAndPrices = false;


    //user go view portfolio
    private boolean viewingPhotos = false;
    //type of photos viewing now
    private String viewingType = "-none-";
    //photoList  To send in chat about 10 images at once
    private List<String> viewingImageList = null;



    private long telegramId = 0;
    private String Name = null;
    private String phone = null;
    private String tgUsername = null;


    @Override
    public String toString() {
        return  "Имя: " + Name + '\n' +
                "Телефон: " + phone + '\n' +
                "Контакт: " + tgUsername + '\n';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerObject that = (CustomerObject) o;
        return telegramId == that.telegramId && Objects.equals(Name, that.Name) && Objects.equals(phone, that.phone) && Objects.equals(tgUsername, that.tgUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, Name, phone, tgUsername);
    }
}
