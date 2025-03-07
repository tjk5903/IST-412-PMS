package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrescribeMedicationView extends JFrame {
    private String userRole;

    // Constructor with userRole parameter
    public PrescribeMedicationView(String userRole) {
        this.userRole = userRole; // Store user role
        setTitle("Prescribe Medication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Create the form components
        JLabel patientLabel = new JLabel("Patient ID: ");
        JTextField patientIdField = new JTextField();
        JLabel medicationLabel = new JLabel("Medication: ");
        JTextField medicationField = new JTextField();
        JLabel dosageLabel = new JLabel("Dosage: ");
        JTextField dosageField = new JTextField();
        JLabel frequencyLabel = new JLabel("Frequency: ");
        JTextField frequencyField = new JTextField();

        // Add components to the layout
        add(patientLabel);
        add(patientIdField);
        add(medicationLabel);
        add(medicationField);
        add(dosageLabel);
        add(dosageField);
        add(frequencyLabel);
        add(frequencyField);

        // Button to submit the prescription
        JButton prescribeButton = new JButton("Prescribe Medication");
        prescribeButton.addActionListener(e -> prescribeMedication(patientIdField.getText(), medicationField.getText(),
                dosageField.getText(), frequencyField.getText()));
        add(prescribeButton);

        // Back button to return to main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton);

        setVisible(true);
    }

    private void prescribeMedication(String patientId, String medication, String dosage, String frequency) {
        // Logic to handle the prescription (you can add validation and database interaction here)
        if (patientId.isEmpty() || medication.isEmpty() || dosage.isEmpty() || frequency.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Medication prescribed successfully to Patient ID: " + patientId, "Success", JOptionPane.INFORMATION_MESSAGE);
            // Add additional logic to save prescription data to the system (e.g., database)
        }
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole); // Pass the user role when returning to the main menu
    }
}
