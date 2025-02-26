package view;

import javax.swing.*;

public class InsuranceVerificationView extends JFrame {
    private JTable insuranceTable;
    private JButton verifyButton;

    public InsuranceVerificationView() {
        setTitle("Insurance Verification");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Patient ID", "Insurance Provider", "Status"};
        Object[][] data = {}; // Populate from database

        insuranceTable = new JTable(data, columnNames);
        verifyButton = new JButton("Verify");

        JPanel panel = new JPanel();
        panel.add(verifyButton);

        add(new JScrollPane(insuranceTable), "Center");
        add(panel, "South");
    }

    public JButton getVerifyButton() {
        return verifyButton;
    }

    public JTable getInsuranceTable() {
        return insuranceTable;
    }
}
