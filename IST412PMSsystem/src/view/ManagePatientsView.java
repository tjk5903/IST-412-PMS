package view;

import model.Admin;
import model.Patient;
import model.UserFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagePatientsView extends JFrame {
    private JTable patientsTable;
    private DefaultTableModel tableModel;
    private JButton addPatientButton, editPatientButton, deletePatientButton, backButton;
    private Admin loggedInAdmin;
    private String userRole;


    public ManagePatientsView(String userRole, Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        this.userRole = userRole;
        setTitle("Manage Patients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Patient ID", "Name", "Age", "Disease"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientsTable = new JTable(tableModel);

        loadPatientsFromDatabase();

        JScrollPane scrollPane = new JScrollPane(patientsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addPatientButton = new JButton("Add Patient");
        editPatientButton = new JButton("Edit Patient");
        deletePatientButton = new JButton("Delete Patient");
        backButton = new JButton("Back");

        addPatientButton.addActionListener(e -> {
            try {
                addPatient();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        editPatientButton.addActionListener(e -> editPatient());
        deletePatientButton.addActionListener(e -> deletePatient());
        backButton.addActionListener(e -> goBack());

        buttonPanel.add(addPatientButton);
        buttonPanel.add(editPatientButton);
        buttonPanel.add(deletePatientButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void loadPatientsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Patient");

            while (rs.next()) {
                int id = rs.getInt("PatientID");
                String name = rs.getString("PatientName");
                int age = rs.getInt("Age");
                String disease = rs.getString("Disease");
                tableModel.addRow(new Object[]{id, name, age, disease});
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patients from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPatient() throws SQLException {
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
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
        String eventLogged = "New patient '" + name + "' created by " + loggedInAdmin.getLogin();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Statement st = conn.createStatement();
        String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                loggedInAdmin.getUserID() + ", '" +
                loggedInAdmin.getLogin().replace("'", "''") + "', '" +
                formattedDateTime.replace("'", "''") + "', '" +
                eventLogged.replace("'", "''") + "')";
        st.executeUpdate(sql);
        JOptionPane.showMessageDialog(this, "Patient Created Successfully");
        conn.close();

        if (patient != null) {
            tableModel.addRow(new Object[]{patient.getUserID(), patient.getName(), age, disease});
        } else {
            JOptionPane.showMessageDialog(this, "Error creating patient. Check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPatient() {
        int selectedRow = patientsTable.getSelectedRow();
        if (selectedRow != -1) {
            String patientName = patientsTable.getValueAt(selectedRow, 1).toString();
            String age = patientsTable.getValueAt(selectedRow, 2).toString();
            String disease = patientsTable.getValueAt(selectedRow, 3).toString();

            String newName = JOptionPane.showInputDialog(this, "Edit Patient Name", patientName);
            String newAge = JOptionPane.showInputDialog(this, "Edit Age", age);
            String newDisease = JOptionPane.showInputDialog(this, "Edit Disease", disease);

            tableModel.setValueAt(newName, selectedRow, 1);
            tableModel.setValueAt(newAge, selectedRow, 2);
            tableModel.setValueAt(newDisease, selectedRow, 3);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = patientsTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int patientID = (int) patientsTable.getValueAt(selectedRow, 0);
                try {
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
                    //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
                    Statement stmt = conn.createStatement();
                    ResultSet patientLogin = stmt.executeQuery("SELECT * FROM User WHERE UserID = " + patientID);
                    String name = "unknown";
                    if (patientLogin.next()) {
                        name = patientLogin.getString("UserLogin");
                    }
                    String eventLogged = "Patient '" + name + "' deleted by " + loggedInAdmin.getLogin();
                    stmt.executeUpdate("DELETE FROM Patient WHERE PatientID = " + patientID);
                    stmt.executeUpdate("DELETE FROM User WHERE UserID = " + patientID);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    Statement st = conn.createStatement();
                    String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                            loggedInAdmin.getUserID() + ", '" +
                            loggedInAdmin.getLogin().replace("'", "''") + "', '" +
                            formattedDateTime.replace("'", "''") + "', '" +
                            eventLogged.replace("'", "''") + "')";
                    st.executeUpdate(sql);
                    conn.close();

                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting patient from database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void goBack() {
        this.dispose();
        new MainMenuView(userRole, loggedInAdmin);
    }
}
