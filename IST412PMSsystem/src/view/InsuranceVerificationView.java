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


        add(patientIDLabel);
        add(patientIDField);
        add(insuranceStatusLabel);
        add(insuranceStatusField);
        add(verifyButton);
        add(backButton);

        setVisible(true);
    }

    private void verifyInsurance() {
        String patientID = patientIDField.getText();


        if (patientID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String insuranceStatus = "Verified";
            insuranceStatusField.setText(insuranceStatus);
        }
    }

    private void goBack() {
        // Go back to the main menu
        this.dispose();
        new MainMenuView("admin");
    }
}
