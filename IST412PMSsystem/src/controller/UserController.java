package controller;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final String DB_URL = "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String contact = rs.getString("UserContact");
                String password = rs.getString("UserPassword");
                String login = rs.getString("UserLogin");
                String role = rs.getString("UserRole");

                users.add(new User(userID, name, contact, password, login, role) {
                    @Override
                    public String getUserInfo() {
                        return name + " (" + role + ")";
                    }

                    @Override
                    public String getRole() {
                        return role;
                    }
                });
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserID = '" + userID + "'");

            if (rs.next()) {
                String name = rs.getString("UserName");
                String contact = rs.getString("UserContact");
                String password = rs.getString("UserPassword");
                String login = rs.getString("UserLogin");
                String role = rs.getString("UserRole");

                conn.close();
                return new User(userID, name, contact, password, login, role) {
                    @Override
                    public String getUserInfo() {
                        return name + " (" + role + ")";
                    }

                    @Override
                    public String getRole() {
                        return role;
                    }
                };
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }


    public boolean deleteUser(String userID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            int affected = stmt.executeUpdate("DELETE FROM User WHERE UserID = '" + userID + "'");
            conn.close();

            return affected > 0;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }
}