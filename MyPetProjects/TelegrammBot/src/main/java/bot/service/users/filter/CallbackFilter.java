package bot.service.users.filter;

import bot.managers.DataManager;
import bot.service.ResponseService;
import bot.managers.KeyboardsManager;
import bot.service.users.manicure.*;
import bot.service.users.portfolio.ShowPortfolio;
import bot.service.users.signup.CallbackSignUpConfirmed;
import bot.simplemessage.SimpleEditMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CallbackFilter extends ResponseService {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        String callback = update.getCallbackQuery().getData();
        long userId = update.getCallbackQuery().getMessage().getChatId();


        switch (callback) {
            case "signUp_confirmOk": {
                if (dataManager.getUserSignUpStatus(userId) == DataManager.userSignUpStatus.SIGNUP_IN_PROGRESS ) {
                    return new CallbackSignUpConfirmed().responseAction(update);
                }
                return List.of(new SimpleEditMessage("Пожалуйста начните процедуру регистрации заново").getNewEditMessage(update));
            }



            case "signUp_editData": {
                if (dataManager.getUserSignUpStatus(userId) == DataManager.userSignUpStatus.SIGNUP_IN_PROGRESS ) {
                    return List.of(
                            new SimpleEditMessage("Повтор ввода данных").getNewEditMessage(update),
                            KeyboardsManager.getSignCancelKeyboard(null, update)
                    );
                }
                return List.of(new SimpleEditMessage("Пожалуйста начните процедуру регистрации заново").getNewEditMessage(update));
            }



            case "viewing_ShowMore": {
                if (dataManager.userIsView(userId)) {
                    return new ShowPortfolio(dataManager.getUserViewingList(userId)).responseAction(update);
                }
            }

            case "viewing_later": {
                if (dataManager.userIsView(userId)) {
                    return List.of(new SimpleEditMessage("Надеемся Вам понравились работы мастера!").getNewEditMessage(update));
                }
            }


            case "BSReg_later":
            case "manicure_reg_cancel_yes": {
                 return new CallbackCancelReg("Запись отменена. Надеемся что вы вернетесь к нам снова "
                        + dataManager.getCustomerObject(userId).getName() + "!").responseAction(update);
            }


            case "manicure_reg_cancel_no": {
                if (dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.NO_REG_ERROR){
                    return List.of(new SimpleEditMessage("Вы не записаны на прием.").getNewEditMessage(update));
                } else
                return List.of(new SimpleEditMessage("С нетепрением будем Вас ждать!").getNewEditMessage(update));
            }






            default: {
                String mtype = update.getCallbackQuery().getData().split("_")[1];

                if (dataManager.getServices().contains(mtype) & dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.SELECT_TYPE ) {
                    return new CallbackSelectWeek().responseAction(update);
                }


                if (callback.startsWith("BSReg_week_")) {
                    if (dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.SELECT_WEEK ) {
                        return new CallbackSelectDay().responseAction(update);
                    }
                }

                if (callback.startsWith("BSReg_day_")) {
                    if (dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.SELECT_DAY ) {
                        return new CallbackSelectHour().responseAction(update);
                    }
                }

                if (callback.startsWith("BSReg_hour_")) {
                    if (dataManager.customerManicureRegStatus(userId) == DataManager.manicureRegStatus.SELECT_HOUR ) {
                        return new CallbackRegComplete().responseAction(update);
                    }
                }



                return List.of(new SimpleEditMessage("Вы ответили на неактуальное сообщение в чате :(").getNewEditMessage(update));
            }
        }
    }
}
