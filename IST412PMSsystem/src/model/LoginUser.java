package model;

public class LoginUser extends User {
    public LoginUser(String userID, String password) {
        super(userID, password);
    }

    @Override
    public String getUserInfo() {
        return name + " (" + role + ")";
    }

    @Override
    public String getRole() {
        return role;
    }
}