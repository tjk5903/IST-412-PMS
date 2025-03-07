package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsuranceVerificationView extends JFrame {
    private JTextField patientIDField, insuranceStatusField;
    private JButton verifyButton, backButton;

    public InsuranceVerificationView() {
        setTitle("Insurance Verification");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel patientIDLabel = new JLabel("Patient ID:");
        JLabel insuranceStatusLabel = new JLabel("Insurance Status:");

        patientIDField = new JTextField();
        insuranceStatusField = new JTextField();
        insuranceStatusField.setEditable(false);  // Can't edit insurance status directly

        verifyButton = new JButton("Verify Insurance");
        backButton = new JButton("Back");

        // Add action listeners
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyInsurance();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        // Add components to the frame
        add(patientIDLabel);
        add(patientIDField);
        add(insuranceStatusLabel);
        add(insuranceStatusField);
        add(verifyButton);
        add(backButton);

        setVisible(true);
    }

    private void verifyInsurance() {
        // Simulate the insurance verification logic
        String patientID = patientIDField.getText();

        // Dummy check for now (You can replace this with a real database/service check)
        if (patientID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // For simplicity, we will simulate the status based on Patient ID
            String insuranceStatus = "Verified";  // This would be dynamic in a real app
            insuranceStatusField.setText(insuranceStatus);
        }
    }

    private void goBack() {
        // Go back to the main menu
        this.dispose();  // Close the current window
        new MainMenuView("admin"); // Assuming 'MainMenuView' is the class for the main menu, passing "admin" as user role
    }
}
