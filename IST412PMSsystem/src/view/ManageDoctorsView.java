package view;

import model.Doctor;
import model.UserFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageDoctorsView extends JFrame {
    private JTable doctorsTable;
    private DefaultTableModel tableModel;
    private JButton addDoctorButton, editDoctorButton, deleteDoctorButton, backButton;

    public ManageDoctorsView() {
        setTitle("Manage Doctors");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for doctors
        String[] columnNames = {"Doctor ID", "Name", "Specialization"};
        tableModel = new DefaultTableModel(columnNames, 0); // Start empty
        doctorsTable = new JTable(tableModel);
        loadDoctorsFromDatabase(); // Fill from DB

        // Scrollable table
        JScrollPane scrollPane = new JScrollPane(doctorsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addDoctorButton = new JButton("Add Doctor");
        editDoctorButton = new JButton("Edit Doctor");
        deleteDoctorButton = new JButton("Delete Doctor");
        backButton = new JButton("Back");

        addDoctorButton.addActionListener(e -> addDoctor());
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

    private void addDoctor() {
        String name = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
        String contact = JOptionPane.showInputDialog(this, "Enter Contact Info:");
        String login = JOptionPane.showInputDialog(this, "Enter Login Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String specialization = JOptionPane.showInputDialog(this, "Enter Specialization:");

        Doctor doctor = UserFactory.createDoctor(name, contact, password, login, specialization);
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
                    stmt.executeUpdate("DELETE FROM Doctor WHERE DoctorID = " + userID);
                    stmt.executeUpdate("DELETE FROM User WHERE UserID = " + userID);
                    conn.close();

                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting doctor from database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        this.dispose();
        new MainMenuView("admin");
    }
}
