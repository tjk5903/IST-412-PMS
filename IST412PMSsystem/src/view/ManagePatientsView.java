package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagePatientsView extends JFrame {
    private JTable patientsTable;
    private DefaultTableModel tableModel;
    private JButton addPatientButton, editPatientButton, deletePatientButton, backButton;

    public ManagePatientsView() {
        setTitle("Manage Patients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for patients
        String[] columnNames = {"Patient ID", "Name", "Age", "Disease"};
        Object[][] data = {
                {"P001", "John Doe", "30", "Flu"},
                {"P002", "Jane Smith", "45", "Diabetes"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        patientsTable = new JTable(tableModel);

        // Scrollable table
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
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

    private void addPatient() {
        // Add a new patient to the table (use dialog for details)
        String patientID = JOptionPane.showInputDialog(this, "Enter Patient ID:");
        String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        String age = JOptionPane.showInputDialog(this, "Enter Age:");
        String disease = JOptionPane.showInputDialog(this, "Enter Disease:");

        // Add to table
        tableModel.addRow(new Object[]{patientID, patientName, age, disease});
    }

    private void editPatient() {
        int selectedRow = patientsTable.getSelectedRow();
        if (selectedRow != -1) {
            String patientID = (String) patientsTable.getValueAt(selectedRow, 0);
            String patientName = (String) patientsTable.getValueAt(selectedRow, 1);
            String age = (String) patientsTable.getValueAt(selectedRow, 2);
            String disease = (String) patientsTable.getValueAt(selectedRow, 3);

            // Edit the patient (using dialog for new details)
            String newName = JOptionPane.showInputDialog(this, "Edit Patient Name", patientName);
            String newAge = JOptionPane.showInputDialog(this, "Edit Age", age);
            String newDisease = JOptionPane.showInputDialog(this, "Edit Disease", disease);

            // Update table
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
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        // Go back to the main menu
        this.dispose();  // Close the current window
        new MainMenuView("admin"); // Assuming 'MainMenuView' is the class for the main menu, passing "admin" as user role
    }
}
