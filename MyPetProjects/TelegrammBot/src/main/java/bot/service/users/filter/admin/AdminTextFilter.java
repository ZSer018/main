package bot.service.users.filter.admin;

import bot.managers.KeyboardsManager;
import bot.service.ResponseService;
import bot.service.admin.SetServiceAndPrice;
import bot.service.users.portfolio.ShowPortfolio;
import bot.simplemessage.SimpleSendMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public class AdminTextFilter extends ResponseService {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> responseAction(Update update) {
        String text = update.getMessage().getText();
        long adminId = dataManager.getAdmin().getTelegramId();

        //TODO удалить свободное окно на определенный час или день
        switch (text) {

            case "Услуги и цены": {
                return List.of(KeyboardsManager.adminServicesAndPricesKeyboard("Раздел 'Услуги и цены' ", update));
            }
            case "Показать услуги и цены": {
                return List.of(new SimpleSendMessage(dataManager.getServicesAndPricesString()).getNewMessage(update));
            }
            case "Удалить услугу": {
                dataManager.getAdmin().setEditServicesAndPrices(true);
                return List.of(
                        KeyboardsManager.getSignCancelKeyboard("Для того что бы удалить услугу из списка, введите:\nзнак минуса '-' и через пробел название услуги.\n\n" +
                                "Например: \n" +
                                "- наращивание", update)
                );
            }
            case "Добавить услугу":
            case "Изменить цену": {
                dataManager.getAdmin().setEditServicesAndPrices(true);
                return List.of(KeyboardsManager.getSignCancelKeyboard("Введите название услуги и через пробел ее цену.\n\n" +
                        "Например: \n" +
                        "Наращивание 3000", update)
                );
            }


            case "<< Назад":
            case "Закончить": {
                dataManager.userSetDefault(adminId);
                return List.of(KeyboardsManager.adminMainKeyboard("Что бы Вы хотели еще, Владыка?", update));
            }


            case "Фотографии": {
                return List.of(KeyboardsManager.adminPhotoManagementKeyboard("Фото", update));
            }
            case "Посмотреть фотографии": {
                dataManager.getAdmin().setViewingPhotos(true);
                return List.of(KeyboardsManager.choosePhotoTypeKeyboard("Владыка! Пожалуйста выберите, фотографии из какого раздела Вам показать", update));
            }
            case "Добавить фотографии": {
                dataManager.getAdmin().setAddingPhotos(true);
                return List.of(
                        KeyboardsManager.choosePhotoTypeKeyboard("Владыка! Выберите раздел, в который будут добавляться фотографии", update)
                );
            }
            case "Удалить фотографии": {
                dataManager.getAdmin().setDeletePhotos(true);
                return List.of(
                        KeyboardsManager.getSignCancelKeyboard("Владыка! Пришлите мне фотографии, которые Вы хотите удалить из базы", update)
                );
            }




            case "Календарь записи": {
                return List.of(KeyboardsManager.adminRegCalendarKeyboard("Просмотр и редактирование календаря записей", update));
            }
            case "Все кто записан": {
                return List.of( new SimpleSendMessage(dataManager.getAllManicureRecords()).getNewMessage(update));
            }
            case "Кто записан сегодня": {
                return List.of( new SimpleSendMessage(dataManager.getTodayManicureRecords()).getNewMessage(update));
            }
            case "Свободные места": {
                return List.of( new SimpleSendMessage(dataManager.getFreeManicureDateTime()).getNewMessage(update));
            }




            case "Сообщение всем": {
                return List.of(KeyboardsManager.adminMainKeyboard("Не реалезовано", update));
            }


            default: {
                if (dataManager.getAdmin().isEditServicesAndPrices()) {
                    return new SetServiceAndPrice().responseAction(update);
                }

                if (dataManager.userIsView(adminId) && dataManager.getServicesByName().contains(text)) {
                    dataManager.setUserViewType(adminId, text);
                    return new ShowPortfolio(null).responseAction(update);
                }

                if (dataManager.getAdmin().isAddingPhotos()) {
                    if (dataManager.getServicesByName().contains(text)) {
                        dataManager.getAdmin().setViewingType(text);
                        return List.of(
                                KeyboardsManager.getSignCancelKeyboard("Владыка! Можете приступить к добавлению фотографий", update)
                        );
                    }
                }

                return List.of(KeyboardsManager.adminMainKeyboard("Что бы Вы хотели еще, Владыка?", update));

            }
        }
    }
}
