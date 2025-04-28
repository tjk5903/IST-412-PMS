package view;

import model.Doctor;
import model.Patient;
import model.User;
import model.LoginUser;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> authenticateUser());
        add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        setVisible(true);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User loginUser = new LoginUser(username, password);
        String role = loginUser.verifyUser();

        if (!role.equals("")) {
            JOptionPane.showMessageDialog(this, "Login Successful! Redirecting...");
            dispose();

            if (role.equalsIgnoreCase("patient")) {
                Patient patient = getPatientFromDatabase(username);
                new MainMenuView(role, patient);
            } else if (role.equalsIgnoreCase("doctor")) {
                Doctor doctor = getDoctorFromDatabase(username);
                new MainMenuView(role, doctor);
            } else {
                new MainMenuView(role);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Patient getPatientFromDatabase(String username) {
        Patient patient = null;
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserLogin = '" + username + "'");
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String contact = rs.getString("UserContact");
                String password = rs.getString("UserPassword");
                String login = rs.getString("UserLogin");
                String role = rs.getString("UserRole");

                int age = 30;
                String gender = "Unknown";
                String address = "Unknown";
                patient = new Patient(userId, name, age, gender, contact, address, password, login, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }

    private Doctor getDoctorFromDatabase(String username) {
        Doctor doctor = null;
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserLogin = '" + username + "'");

            if (rs.next()) {
                int userId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String contact = rs.getString("UserContact");
                String password = rs.getString("UserPassword");
                String login = rs.getString("UserLogin");
                String role = rs.getString("UserRole");

                String specialty = "General";
                doctor = new Doctor(userId, name, specialty, contact, password, login, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctor;
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
