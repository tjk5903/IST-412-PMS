package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Abstract class for User
public abstract class User {
    protected String userID;
    protected String name;
    protected String contact;
    protected String role;  // Could be "Doctor", "Patient", or "Admin"
    protected String password;

    public User(String userID, String name, String contact, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.password = password;
        this.role = role;
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public User() {}

    public abstract String getUserInfo();
    public abstract String getRole();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String verifyUser(){
        String roleID = "";
        try {
            // Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select UserRole, UserName from User " +
                    "where UserLogin = \"" + userID + "\" and UserPassword = \""
                    + password + "\"" );
            if(rs.next()){
                System.out.println("User Authenticated");
                roleID = rs.getString("UserRole");
                this.role = rs.getString("UserRole");
                this.name = rs.getString("UserName");
            }
        }catch(Exception ee){System.out.println(ee);}
        return roleID;
    }
}