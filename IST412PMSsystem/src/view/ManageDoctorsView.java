package view;

import model.Admin;
import model.Doctor;
import model.UserFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManageDoctorsView extends JFrame {
    private JTable doctorsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> specDropDown;
    private Admin loggedInAdmin;
    private String userRole;

    private JButton addDoctorButton, editDoctorButton, deleteDoctorButton, backButton;

    public ManageDoctorsView(String userRole, Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        this.userRole = userRole;
        setTitle("Manage Doctors");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Doctor ID", "Name", "Specialization"};
        tableModel = new DefaultTableModel(columnNames, 0);
        doctorsTable = new JTable(tableModel);
        loadDoctorsFromDatabase();

        JScrollPane scrollPane = new JScrollPane(doctorsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addDoctorButton = new JButton("Add Doctor");
        editDoctorButton = new JButton("Edit Doctor");
        deleteDoctorButton = new JButton("Delete Doctor");
        backButton = new JButton("Back");

        addDoctorButton.addActionListener(e -> {
            try {
                addDoctor();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        editDoctorButton.addActionListener(e -> editDoctor());
        deleteDoctorButton.addActionListener(e -> deleteDoctor());
        backButton.addActionListener(e -> goBack());

        buttonPanel.add(addDoctorButton);
        buttonPanel.add(editDoctorButton);
        buttonPanel.add(deleteDoctorButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadDoctorsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Doctor");

            while (rs.next()) {
                int id = rs.getInt("DoctorID");
                String name = rs.getString("DoctorName");
                String specialization = rs.getString("Specialty");
                tableModel.addRow(new Object[]{id, name, specialization});
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctors from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDoctor() throws SQLException {
        String name = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
        if (name == null) return;
        String contact = JOptionPane.showInputDialog(this, "Enter Contact Info:");
        if (contact == null) return;
        String login = null;
        while (true) {
            login = JOptionPane.showInputDialog(this, "Enter Login Username:");
            if (login == null || login.trim().isEmpty()) return;

            if (checkUsername(login.trim())) {
                JOptionPane.showMessageDialog(this, "That username is already taken. Please choose a different one.", "Username Taken", JOptionPane.WARNING_MESSAGE);
            } else {
                break;
            }
        }
        if (checkUsername(login)) {
            JOptionPane.showMessageDialog(this, "That username is already taken. Please choose a different one.", "Username Taken", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        if (password == null) return;
        JComboBox<String> specDropDown = new JComboBox<>();
        specDropDown.addItem("Cardiologist");
        specDropDown.addItem("Neurologist");
        specDropDown.addItem("Dermatologist");
        specDropDown.addItem("Physician");
        specDropDown.addItem("Psychiatrist");
        specDropDown.addItem("Other");
        int result = JOptionPane.showConfirmDialog(this, specDropDown, "Select Specialization", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        String specialization = (String) specDropDown.getSelectedItem();
        Doctor doctor = UserFactory.createDoctor(name, contact, password, login, specialization);
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
        String eventLogged = "New doctor '" + name + "' created by " + loggedInAdmin.getLogin();
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
        JOptionPane.showMessageDialog(this, "Doctor Created Successfully");
        conn.close();
        if (doctor != null) {
            tableModel.addRow(new Object[]{doctor.getUserID(), doctor.getName(), specialization});
        } else {
            JOptionPane.showMessageDialog(this, "Error creating doctor. Check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editDoctor() {
        int selectedRow = doctorsTable.getSelectedRow();
        if (selectedRow != -1) {
            String doctorName = doctorsTable.getValueAt(selectedRow, 1).toString();
            String specialization = doctorsTable.getValueAt(selectedRow, 2).toString();

            String newName = JOptionPane.showInputDialog(this, "Edit Doctor Name", doctorName);
            String newSpecialization = JOptionPane.showInputDialog(this, "Edit Specialization", specialization);

            tableModel.setValueAt(newName, selectedRow, 1);
            tableModel.setValueAt(newSpecialization, selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a doctor to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDoctor() {
        int selectedRow = doctorsTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int userID = (int) doctorsTable.getValueAt(selectedRow, 0);
                try {
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
                    //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
                    Statement stmt = conn.createStatement();
                    ResultSet patientLogin = stmt.executeQuery("SELECT * FROM User WHERE UserID = " + userID);
                    String name = "unknown";
                    if (patientLogin.next()) {
                        name = patientLogin.getString("UserLogin");
                    }
                    String eventLogged = "Doctor '" + name + "' deleted by " + loggedInAdmin.getLogin();
                    stmt.executeUpdate("DELETE FROM Doctor WHERE DoctorID = " + userID);
                    stmt.executeUpdate("DELETE FROM User WHERE UserID = " + userID);
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
                    JOptionPane.showMessageDialog(this, "Doctor Deleted Successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting doctor from database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkUsername(String username) {
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
