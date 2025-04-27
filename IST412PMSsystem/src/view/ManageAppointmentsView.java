package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAppointmentsView extends JFrame {
    private String userRole;
    private DefaultTableModel model;
    private JTable table;

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
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

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
        cancelButton.addActionListener(e -> cancelAppointment());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button to go back to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private void addAppointment() {
        // Open a dialog to add a new appointment
        JTextField appointmentIdField = new JTextField(10);
        JTextField doctorField = new JTextField(10);
        JTextField dateField = new JTextField(10);
        JTextField timeField = new JTextField(10);
        JTextField statusField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Appointment ID:"));
        panel.add(appointmentIdField);
        panel.add(new JLabel("Doctor:"));
        panel.add(doctorField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Time (HH:MM AM/PM):"));
        panel.add(timeField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Enter Appointment Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Add new row to the table (this should be validated in real use cases)
            String appointmentId = appointmentIdField.getText();
            String doctor = doctorField.getText();
            String date = dateField.getText();
            String time = timeField.getText();
            String status = statusField.getText();

            // Add new appointment to the model
            model.addRow(new Object[]{appointmentId, doctor, date, time, status});
        }
    }

    private void cancelAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Get the Appointment ID and cancel the selected appointment
            String appointmentId = (String) table.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(this, "Canceling appointment: " + appointmentId);
            // Remove the canceled appointment from the table
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
        }
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole); // Pass the user role when returning to the main menu
    }
}
