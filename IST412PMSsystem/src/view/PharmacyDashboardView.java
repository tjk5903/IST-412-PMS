package view;

import javax.swing.*;

public class PharmacyDashboardView extends JFrame {
    private JTable prescriptionsTable;
    private JButton dispenseButton;

    public PharmacyDashboardView() {
        setTitle("Pharmacy Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Prescription ID", "Medication", "Dosage", "Status"};
        Object[][] data = {}; // Should be fetched from database

        prescriptionsTable = new JTable(data, columnNames);
        dispenseButton = new JButton("Dispense");

        JPanel panel = new JPanel();
        panel.add(dispenseButton);

        add(new JScrollPane(prescriptionsTable), "Center");
        add(panel, "South");
    }

    public JButton getDispenseButton() {
        return dispenseButton;
    }

    public JTable getPrescriptionsTable() {
        return prescriptionsTable;
    }
}
