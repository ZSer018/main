package data.util;

import data.dao.UserDao;
import data.entity.User;


public class AccountManager {

    private static AccountManager accountManager;

    private AccountManager() {
    }

    public static AccountManager getInstance(){
        if (accountManager == null) {
            accountManager = new AccountManager();
        }

        return accountManager;
    }

    public boolean checkUserData(User user){
        User userData = new UserDao().read(user.getLogin());

        if (userData != null) {
            return userData.getPass().equals(user.getPass());
        }

        return false;
    }

    public boolean isRegistred(User userProfile){
        return new UserDao().read(userProfile.getLogin()) != null;
    }

    public boolean addNewUser(User userProfile){
        new UserDao().save(userProfile);
        return true;
    }

    public boolean deleteUser(User userProfile){
        return false;
    }

}
