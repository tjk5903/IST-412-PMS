package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewPatientRecordsView extends JFrame {
    private String userRole;

    // Constructor with userRole parameter
    public ViewPatientRecordsView(String userRole) {
        this.userRole = userRole; // Store user role
        setTitle("View Patient Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a table to display patient records
        String[] columns = {"Patient ID", "Name", "Age", "Diagnosis", "Medication"};
        Object[][] data = {
                {"P001", "John Doe", 30, "Flu", "Medicine A"},
                {"P002", "Jane Smith", 25, "Cold", "Medicine B"},
                {"P003", "Mike Johnson", 40, "Allergy", "Medicine C"}
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
