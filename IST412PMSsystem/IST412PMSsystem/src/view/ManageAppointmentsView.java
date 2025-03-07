package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAppointmentsView extends JFrame {
    private String userRole;

    // Constructor with userRole parameter
    public ManageAppointmentsView(String userRole) {
        this.userRole = userRole; // Store user role
        setTitle("Manage Appointments");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a table to display current appointments
        String[] columns = {"Appointment ID", "Doctor", "Date", "Time", "Status"};
        Object[][] data = {
                {"A001", "Dr. Smith", "2025-01-15", "10:00 AM", "Scheduled"},
                {"A002", "Dr. Johnson", "2025-01-20", "02:00 PM", "Scheduled"},
                {"A003", "Dr. Lee", "2025-02-05", "11:00 AM", "Scheduled"}
        };

        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // Add table to scroll pane for better usability
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons (Add Appointment and Cancel Appointment)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add New Appointment");
        addButton.addActionListener(e -> addAppointment());
        buttonPanel.add(addButton);

        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.addActionListener(e -> cancelAppointment(table));
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button to go back to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private void addAppointment() {
        // You can create a dialog or new window to add a new appointment
        JOptionPane.showMessageDialog(this, "Adding a new appointment...");
        // Logic for adding an appointment would go here (e.g., open another view for scheduling)
    }

    private void cancelAppointment(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Get the Appointment ID and cancel the selected appointment
            String appointmentId = (String) table.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(this, "Canceling appointment: " + appointmentId);
            // Logic for canceling the appointment would go here (e.g., remove from database)
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
        }
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole); // Pass the user role when returning to the main menu
    }
}
