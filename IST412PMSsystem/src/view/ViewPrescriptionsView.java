package view;

import controller.PrescriptionController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPrescriptionsView extends JFrame {
    private JList<String> prescriptionList;
    private DefaultListModel<String> listModel;
    private JButton refreshButton;
    private int patientID; // Store the patient ID

    public ViewPrescriptionsView(int patientID) {
        this.patientID = patientID; // Store the patient ID
        setTitle("Prescription History");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        listModel = new DefaultListModel<>();
        prescriptionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionList);
        scrollPane.setBounds(20, 20, 350, 200);
        add(scrollPane);

        refreshButton = new JButton("Refresh Prescriptions");
        refreshButton.setBounds(100, 230, 200, 30);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPrescriptions();
            }
        });
        add(refreshButton);

        // Load initial prescriptions
        refreshPrescriptions();

        setVisible(true);
    }

    private void refreshPrescriptions() {
        listModel.clear(); // Clear the current list model
        List<String> prescriptions = PrescriptionController.getPatientPrescriptions(patientID); // Pass patientID to the method
        if (prescriptions.isEmpty()) {
            listModel.addElement("No prescriptions found.");
        } else {
            for (String prescription : prescriptions) {
                listModel.addElement(prescription);
            }
        }
    }
}
