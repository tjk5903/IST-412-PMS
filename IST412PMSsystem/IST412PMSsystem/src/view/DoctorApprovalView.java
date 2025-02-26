package view;

import controller.DoctorController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorApprovalView extends JFrame {
    private JList<String> prescriptionList;
    private JButton approveButton, rejectButton;
    private DefaultListModel<String> listModel;

    public DoctorApprovalView() {
        setTitle("Approve Prescriptions");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        listModel = new DefaultListModel<>();
        prescriptionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionList);
        scrollPane.setBounds(20, 20, 350, 150);
        add(scrollPane);

        List<String> pendingPrescriptions = DoctorController.getPendingPrescriptions();
        for (String prescription : pendingPrescriptions) {
            listModel.addElement(prescription);
        }

        approveButton = new JButton("Approve");
        approveButton.setBounds(50, 200, 120, 30);
        approveButton.addActionListener(e -> {
            String selected = prescriptionList.getSelectedValue();
            if (selected != null) {
                DoctorController.approvePrescription(selected);
                JOptionPane.showMessageDialog(this, "Prescription Approved!");
                listModel.removeElement(selected);
            }
        });

        rejectButton = new JButton("Reject");
        rejectButton.setBounds(200, 200, 120, 30);
        rejectButton.addActionListener(e -> {
            String selected = prescriptionList.getSelectedValue();
            if (selected != null) {
                DoctorController.rejectPrescription(selected);
                JOptionPane.showMessageDialog(this, "Prescription Rejected!");
                listModel.removeElement(selected);
            }
        });

        add(approveButton);
        add(rejectButton);
        setVisible(true);
    }
}
