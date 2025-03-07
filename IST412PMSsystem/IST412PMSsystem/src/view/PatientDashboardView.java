package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientDashboardView extends JFrame {
    private JButton orderPrescriptionButton;
    private JButton viewPrescriptionsButton;
    private int patientID; // Example of an argument

    /**
     * Constructs the PatientDashboardView with a patient ID.
     *
     * @param patientID The ID of the patient.
     */
    public PatientDashboardView(int patientID) {
        this.patientID = patientID; // Store the patient ID
        setTitle("Patient Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        orderPrescriptionButton = new JButton("Order Prescription");
        orderPrescriptionButton.setBounds(100, 50, 200, 30);
        orderPrescriptionButton.addActionListener(e -> new OrderPrescriptionView(patientID)); // Pass the patient ID

        viewPrescriptionsButton = new JButton("View Prescriptions");
        viewPrescriptionsButton.setBounds(100, 100, 200, 30);
        viewPrescriptionsButton.addActionListener(e -> new ViewPrescriptionsView(patientID)); // Pass the patient ID

        add(orderPrescriptionButton);
        add(viewPrescriptionsButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        int examplePatientID = 1; // Example patient ID, replace with actual value as needed
        new PatientDashboardView(examplePatientID);
    }
}
