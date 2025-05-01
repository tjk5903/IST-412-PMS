package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private int failedAttempts = 0;
    private long lockoutEndTime = 0;

    public LoginView() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            try {
                authenticateUser();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        formPanel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        formPanel.add(exitButton);

        add(formPanel, BorderLayout.CENTER);

        JButton newUser = new JButton("Create Account");
        newUser.addActionListener(e -> {
            createNewPatient();});
        add(newUser, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void authenticateUser() throws SQLException {
        long currentTime = System.currentTimeMillis();

        if (currentTime < lockoutEndTime) {
            long secondsLeft = (lockoutEndTime - currentTime) / 1000;
            JOptionPane.showMessageDialog(this,
                    "Too many failed attempts. Please wait " + secondsLeft + " seconds.",
                    "Account Locked", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User loginUser = new LoginUser(username, password);
        String role = loginUser.verifyUser();

        if (!role.equals("")) {
            failedAttempts = 0;
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            JOptionPane.showMessageDialog(this, "Login Successful! Redirecting...");
            String eventLogged = "Successful login by " + username;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Statement st = conn.createStatement();
            ResultSet userLogin = st.executeQuery("SELECT * FROM User WHERE UserLogin = '" + username.replace("'", "''") + "'");
            int ID = 0;
            if (userLogin.next()) {
                ID = Integer.parseInt(userLogin.getString("ID"));
            }
            String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                    ID + ", '" +
                    username.replace("'", "''") + "', '" +
                    formattedDateTime.replace("'", "''") + "', '" +
                    eventLogged.replace("'", "''") + "')";
            st.executeUpdate(sql);
            conn.close();
            dispose();

            if (role.equalsIgnoreCase("patient")) {
                Patient patient = getPatientFromDatabase(username);
                new MainMenuView(role, patient);
            } else if (role.equalsIgnoreCase("doctor")) {
                Doctor doctor = getDoctorFromDatabase(username);
                new MainMenuView(role, doctor);
            } else {
                Admin admin = getAdminFromDatabase(username);
                new MainMenuView(role, admin);
            }
        } else {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Statement st = conn.createStatement();
            failedAttempts++;
            if (failedAttempts >= 3) {
                lockoutEndTime = currentTime + 10_000;
                JOptionPane.showMessageDialog(this,
                        "Too many failed attempts. You are locked out for 10 seconds.",
                        "Account Locked", JOptionPane.ERROR_MESSAGE);
                String eventLogged = "Failed login - Lockout: username=" + username + ", password=" + password;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String noUser = "N/A";
                String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        0 + ", '" +
                        noUser.replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
                st.executeUpdate(sql);
            } else {
                int attemptsLeft = 3 - failedAttempts;
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password! (" + attemptsLeft + " attempts left)",
                        "Error", JOptionPane.ERROR_MESSAGE);
                String eventLogged = "Failed login: username=" + username + ", password=" + password;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String noUser = "N/A";
                String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        0 + ", '" +
                        noUser.replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
                st.executeUpdate(sql);
            }
            conn.close();
        }
    }

    private void createNewPatient(){
        String name = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        if (name == null) return;
        String contact = JOptionPane.showInputDialog(this, "Enter Contact Info:");
        if (contact == null) return;
        String login = null;
        while (true) {
            login = JOptionPane.showInputDialog(this, "Enter Login Username:");
            if (login == null || login.trim().isEmpty()) return;

            if (isUsernameTaken(login.trim())) {
                JOptionPane.showMessageDialog(this, "That username is already taken. Please choose a different one.", "Username Taken", JOptionPane.WARNING_MESSAGE);
            } else {
                break;
            }
        }
        if (isUsernameTaken(login)) {
            JOptionPane.showMessageDialog(this, "That username is already taken. Please choose a different one.", "Username Taken", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        if (password == null) return;

        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Integer age = null;
            while (true) {
                String ageStr = JOptionPane.showInputDialog(this, "Enter Age:");
                if (ageStr == null || ageStr.trim().isEmpty()) return;

                try {
                    age = Integer.valueOf(ageStr.trim());
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            JComboBox<String> diseaseDropDown = new JComboBox<>();
            diseaseDropDown.addItem("Flu");
            diseaseDropDown.addItem("COVID-19");
            diseaseDropDown.addItem("Cold");
            diseaseDropDown.addItem("Strep");
            diseaseDropDown.addItem("Pneumonia");
            diseaseDropDown.addItem("Other");
            int result = JOptionPane.showConfirmDialog(this, diseaseDropDown, "Select Specialization", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String disease = (String) diseaseDropDown.getSelectedItem();
            Patient patient = UserFactory.createPatient(name, contact, password, login, age, disease);

            if (patient != null) {
                dispose();
                String noUser = "N/A";
                String eventLogged = "New patient '" + name + "' created at login screen";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                Statement st = conn.createStatement();
                String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        0 + ", '" +
                        noUser.replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(this, "Patient Created Successfully");
                conn.close();
                new LoginView();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating patient. Check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age entered.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private Admin getAdminFromDatabase(String username) {
        Admin admin = null;
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM [User] WHERE UserLogin = '" + username + "'");
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String contact = rs.getString("UserContact");
                String password = rs.getString("UserPassword");
                String login = rs.getString("UserLogin");
                String role = rs.getString("UserRole");
                admin = new Admin(userId, name, contact, password, login, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }


    private boolean isUsernameTaken(String username) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM [User] WHERE UserLogin = '" + username + "'");
            if (rs.next()) {
                int count = rs.getInt("count");
                conn.close();
                return count > 0;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking username.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
