package view;

import view.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private HashMap<String, String[]> users; // Stores dummy user data

    public LoginView() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Dummy user data: username -> {password, role}
        users = new HashMap<>();
        users.put("doctor1", new String[]{"pass123", "doctor"});
        users.put("patient1", new String[]{"pass123", "patient"});
        users.put("pharmacist1", new String[]{"pass123", "pharmacist"});
        users.put("admin1", new String[]{"adminpass", "admin"});

        // UI Elements
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

        if (users.containsKey(username) && users.get(username)[0].equals(password)) {
            String role = users.get(username)[1];
            JOptionPane.showMessageDialog(this, "Login Successful! Redirecting...");
            dispose(); // Close login window
            new MainMenuView(role); // Open Main Menu based on role
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Test the LoginView
    public static void main(String[] args) {
        new LoginView();
    }
}
