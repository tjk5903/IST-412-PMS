package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class User {
    protected int userID;
    protected String name;
    protected String contact;
    protected String role;
    protected String password;
    protected String login;

    public User(int userID, String name, String contact, String password, String login, String role) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.password = password;
        this.login = login;
        this.role = role;
    }

    public User(int userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public User() {}

    public abstract String getUserInfo();
    public abstract String getRole();

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public void setRole(String role) { this.role = role; }

    public String verifyUser() {
        String roleID = "";
        try {
            //Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT UserRole, UserName FROM User WHERE UserLogin = " +
                            "'" + login + "' AND UserPassword = '" + password + "'");

            if (rs.next()) {
                System.out.println("User Authenticated");
                roleID = rs.getString("UserRole");
                this.role = roleID;
                this.name = rs.getString("UserName");
            }

        } catch (Exception ee) {
            System.out.println("Error verifying user: " + ee.getMessage());
        }

        return roleID;
    }
}