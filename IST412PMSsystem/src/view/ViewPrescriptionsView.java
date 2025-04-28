package view;

import repository.PrescriptionRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ViewPrescriptionsView extends JFrame {
    private String userRole;
    private Connection connection;

    // Constructor with userRole and Connection parameters
    public ViewPrescriptionsView(String userRole) {
        this.userRole = userRole;
        this.connection = connection;

        setTitle("View Prescriptions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Prescription ID", "Medication", "Dosage", "Start Date", "End Date"};

        PrescriptionRepository repo = new PrescriptionRepository(connection);
        List<String[]> prescriptionList = repo.getAllPrescriptions();

        Object[][] data = prescriptionList.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole); // You might need to also pass connection depending on how your MainMenuView is set up
    }
}
