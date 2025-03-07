package view;

import javax.swing.*;

public class OrderPrescriptionView extends JFrame {
    private int patientID; // To store the patient ID

    public OrderPrescriptionView(int patientID) {
        this.patientID = patientID; // Store the patient ID
        setTitle("Order Prescription");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Add components related to ordering a prescription
        // For example, you might want to add fields for medication and dosage

        setVisible(true);
    }
}
