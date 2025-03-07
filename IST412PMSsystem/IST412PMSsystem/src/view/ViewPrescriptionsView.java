package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewPrescriptionsView extends JFrame {
    private String userRole;

    // Constructor with userRole parameter
    public ViewPrescriptionsView(String userRole) {
        this.userRole = userRole; // Store user role
        setTitle("View Prescriptions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a table to display prescription records
        String[] columns = {"Prescription ID", "Medication", "Dosage", "Start Date", "End Date"};
        Object[][] data = {
                {"RX001", "Medicine A", "1 pill daily", "2025-01-01", "2025-02-01"},
                {"RX002", "Medicine B", "2 pills daily", "2025-01-10", "2025-02-10"},
                {"RX003", "Medicine C", "1 pill twice daily", "2025-02-05", "2025-03-05"}
        };

        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // Add table to scroll pane for better usability
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button to go back to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole); // Pass the user role when returning to the main menu
    }
}
