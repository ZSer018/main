package utils;

import java.util.HashMap;

public class AccountManager {

    private final HashMap<String, UserProfile> registerUsers;


    public AccountManager() {
        registerUsers = new HashMap<>();
    }

    public boolean addNewUser(UserProfile userProfile){
        if (registerUsers.containsKey(userProfile.getLogin())){
            return false;
        }
        registerUsers.put(userProfile.getLogin(), userProfile);
        return true;
    }

    public boolean deleteUser(UserProfile userProfile){
        if (registerUsers.containsKey(userProfile.getLogin())){
            return false;
        }
        registerUsers.remove(userProfile.getLogin());
        return true;
    }

    public boolean checkUserData(UserProfile userProfile){
        if (registerUsers.containsKey(userProfile.getLogin())){
            return userProfile.getPass().equals(registerUsers.get(userProfile.getLogin()).getPass());
        }
        return false;
    }

}
