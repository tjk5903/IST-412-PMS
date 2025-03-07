package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessPrescriptionsView extends JFrame {
    private JTable prescriptionTable;
    private JButton processButton, cancelButton, backButton;
    private DefaultTableModel tableModel;

    public ProcessPrescriptionsView() {
        setTitle("Process Prescriptions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the table model
        String[] columnNames = {"Prescription ID", "Patient Name", "Medication", "Doctor", "Status"};
        Object[][] data = {
                {"001", "John Doe", "Aspirin", "Dr. Smith", "Pending"},
                {"002", "Jane Smith", "Ibuprofen", "Dr. Williams", "Pending"},
                {"003", "Emily Brown", "Paracetamol", "Dr. Jones", "Pending"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        prescriptionTable = new JTable(tableModel);

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        processButton = new JButton("Process Prescription");
        cancelButton = new JButton("Cancel Prescription");
        backButton = new JButton("Back");

        // Disable buttons initially
        processButton.setEnabled(false);
        cancelButton.setEnabled(false);

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPrescription();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelPrescription();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        buttonPanel.add(processButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add row selection listener to enable buttons when a row is selected
        prescriptionTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = prescriptionTable.getSelectedRow();
            if (selectedRow != -1) {
                processButton.setEnabled(true);
                cancelButton.setEnabled(true);
            } else {
                processButton.setEnabled(false);
                cancelButton.setEnabled(false);
            }
        });

        setVisible(true);
    }

    private void processPrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        String prescriptionId = (String) prescriptionTable.getValueAt(selectedRow, 0);
        String patientName = (String) prescriptionTable.getValueAt(selectedRow, 1);
        String medication = (String) prescriptionTable.getValueAt(selectedRow, 2);

        // Logic to process the prescription
        JOptionPane.showMessageDialog(this, "Processing Prescription: " + prescriptionId + " for " + patientName + " (" + medication + ")");

        // After processing, you could update the status to 'Processed'
        tableModel.setValueAt("Processed", selectedRow, 4);
    }

    private void cancelPrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        String prescriptionId = (String) prescriptionTable.getValueAt(selectedRow, 0);

        // Logic to cancel the prescription
        JOptionPane.showMessageDialog(this, "Cancelling Prescription: " + prescriptionId);

        // After cancellation, you could update the status to 'Cancelled'
        tableModel.setValueAt("Cancelled", selectedRow, 4);
    }

    private void goBack() {
        // Close this view and return to the main menu
        this.dispose();
        new MainMenuView("pharmacist"); // Or whatever user role is appropriate
    }
}
