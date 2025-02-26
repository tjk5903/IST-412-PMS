package view;

import controller.PharmacyController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PharmacyProcessingView extends JFrame {
    private JList<String> prescriptionList;
    private JButton processButton;
    private DefaultListModel<String> listModel;

    public PharmacyProcessingView() {
        setTitle("Process Prescriptions");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        listModel = new DefaultListModel<>();
        prescriptionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionList);
        scrollPane.setBounds(20, 20, 350, 150);
        add(scrollPane);

        List<String> approvedPrescriptions = PharmacyController.getApprovedPrescriptions();
        for (String prescription : approvedPrescriptions) {
            listModel.addElement(prescription);
        }

        processButton = new JButton("Process Prescription");
        processButton.setBounds(100, 200, 200, 30);
        processButton.addActionListener(e -> {
            String selected = prescriptionList.getSelectedValue();
            if (selected != null) {
                PharmacyController.processPrescription(selected);
                JOptionPane.showMessageDialog(this, "Prescription Processed!");
                listModel.removeElement(selected);
            }
        });

        add(processButton);
        setVisible(true);
    }
}
