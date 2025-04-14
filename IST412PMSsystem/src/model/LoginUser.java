package model;

public class LoginUser extends User {
    public LoginUser(String login, String password) {
        this.login = login;
        this.password = password;
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