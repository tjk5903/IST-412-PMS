package view;

import model.Patient;
import model.UserFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManagePatientsView extends JFrame {
    private JTable patientsTable;
    private DefaultTableModel tableModel;
    private JButton addPatientButton, editPatientButton, deletePatientButton, backButton;

    public ManagePatientsView() {
        setTitle("Manage Patients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table column headers
        String[] columnNames = {"Patient ID", "Name", "Age", "Disease"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientsTable = new JTable(tableModel);

        // Load from database
        loadPatientsFromDatabase();

        // Scrollable table
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addPatientButton = new JButton("Add Patient");
        editPatientButton = new JButton("Edit Patient");
        deletePatientButton = new JButton("Delete Patient");
        backButton = new JButton("Back");

        addPatientButton.addActionListener(e -> addPatient());
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
            //Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
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

    private void addPatient() {
        String name = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        String contact = JOptionPane.showInputDialog(this, "Enter Contact Info:");
        String login = JOptionPane.showInputDialog(this, "Enter Login Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Age:"));
        String disease = JOptionPane.showInputDialog(this, "Enter Disease:");

        Patient patient = UserFactory.createPatient(name, contact, password, login, age, disease);

        if (patient != null) {
            tableModel.addRow(new Object[]{patient.getUserID(), patient.getName(), age, disease});
        } else {
            JOptionPane.showMessageDialog(this, "Error creating patient. Check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPatient() {
        int selectedRow = patientsTable.getSelectedRow();
        if (selectedRow != -1) {
            String patientID = patientsTable.getValueAt(selectedRow, 0).toString();
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
                String patientName = (String) patientsTable.getValueAt(selectedRow, 1);  // Assuming column 1 is the Name
                try {
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
                    //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT PatientID FROM Patient WHERE PatientName = '" + patientName + "'");
                    if (rs.next()) {
                        int userID = rs.getInt("PatientID");
                        stmt.executeUpdate("DELETE FROM Patient WHERE PatientID = " + userID);
                        stmt.executeUpdate("DELETE FROM User WHERE UserID = " + userID);
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Patient not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    rs.close();
                    stmt.close();
                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting patient from database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        this.dispose();
        new MainMenuView("admin");
    }
}