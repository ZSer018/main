package bot.newbot.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpObject {
    private boolean awaitingDataInput = false;
    private long telegramId;
    private String Name;
    private String phone;
    private String tgUsername;

}
