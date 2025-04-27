package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ViewPatientRecordsView extends JFrame {
    private String userRole;
    private JTable table;
    private DefaultTableModel model;

    public ViewPatientRecordsView(String userRole) {
        this.userRole = userRole; // Store user role
        setTitle("View Patient Records");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Patient ID", "Name", "Age", "Diagnosis", "Medication"};
        Object[][] data = {
                {"P001", "John Doe", 30, "Flu", "Medicine A"},
                {"P002", "Jane Smith", 25, "Cold", "Medicine B"},
                {"P003", "Mike Johnson", 40, "Allergy", "Medicine C"}
        };

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        buttonPanel.add(backButton);

        if (userRole.equalsIgnoreCase("admin") || userRole.equalsIgnoreCase("doctor")) {
            JButton editButton = new JButton("Edit Selected Record");
            editButton.addActionListener(e -> editSelectedRecord());
            buttonPanel.add(editButton);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole);
    }

    private void editSelectedRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.");
            return;
        }

        String patientId = (String) model.getValueAt(selectedRow, 0);
        String currentName = (String) model.getValueAt(selectedRow, 1);
        int currentAge = (Integer) model.getValueAt(selectedRow, 2);
        String currentDiagnosis = (String) model.getValueAt(selectedRow, 3);
        String currentMedication = (String) model.getValueAt(selectedRow, 4);

        JTextField nameField = new JTextField(currentName);
        JTextField ageField = new JTextField(String.valueOf(currentAge));
        JTextField diagnosisField = new JTextField(currentDiagnosis);
        JTextField medicationField = new JTextField(currentMedication);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Diagnosis:"));
        panel.add(diagnosisField);
        panel.add(new JLabel("Medication:"));
        panel.add(medicationField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Patient Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String updatedName = nameField.getText();
                int updatedAge = Integer.parseInt(ageField.getText());
                String updatedDiagnosis = diagnosisField.getText();
                String updatedMedication = medicationField.getText();

                // Update the model with new values
                model.setValueAt(updatedName, selectedRow, 1);
                model.setValueAt(updatedAge, selectedRow, 2);
                model.setValueAt(updatedDiagnosis, selectedRow, 3);
                model.setValueAt(updatedMedication, selectedRow, 4);


                JOptionPane.showMessageDialog(this, "Record updated successfully!");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
