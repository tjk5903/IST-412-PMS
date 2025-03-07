package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageDoctorsView extends JFrame {
    private JTable doctorsTable;
    private DefaultTableModel tableModel;
    private JButton addDoctorButton, editDoctorButton, deleteDoctorButton, backButton;

    public ManageDoctorsView() {
        setTitle("Manage Doctors");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for doctors
        String[] columnNames = {"Doctor ID", "Name", "Specialization"};
        Object[][] data = {
                {"D001", "Dr. Smith", "Cardiology"},
                {"D002", "Dr. Williams", "Neurology"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        doctorsTable = new JTable(tableModel);

        // Scrollable table
        JScrollPane scrollPane = new JScrollPane(doctorsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addDoctorButton = new JButton("Add Doctor");
        editDoctorButton = new JButton("Edit Doctor");
        deleteDoctorButton = new JButton("Delete Doctor");
        backButton = new JButton("Back");

        addDoctorButton.addActionListener(e -> addDoctor());
        editDoctorButton.addActionListener(e -> editDoctor());
        deleteDoctorButton.addActionListener(e -> deleteDoctor());
        backButton.addActionListener(e -> goBack());

        buttonPanel.add(addDoctorButton);
        buttonPanel.add(editDoctorButton);
        buttonPanel.add(deleteDoctorButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addDoctor() {
        // Add a new doctor to the table (use dialog for details)
        String doctorID = JOptionPane.showInputDialog(this, "Enter Doctor ID:");
        String doctorName = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
        String specialization = JOptionPane.showInputDialog(this, "Enter Specialization:");

        // Add to table
        tableModel.addRow(new Object[]{doctorID, doctorName, specialization});
    }

    private void editDoctor() {
        int selectedRow = doctorsTable.getSelectedRow();
        if (selectedRow != -1) {
            String doctorID = (String) doctorsTable.getValueAt(selectedRow, 0);
            String doctorName = (String) doctorsTable.getValueAt(selectedRow, 1);
            String specialization = (String) doctorsTable.getValueAt(selectedRow, 2);

            // Edit the doctor (using dialog for new details)
            String newName = JOptionPane.showInputDialog(this, "Edit Doctor Name", doctorName);
            String newSpecialization = JOptionPane.showInputDialog(this, "Edit Specialization", specialization);

            // Update table
            tableModel.setValueAt(newName, selectedRow, 1);
            tableModel.setValueAt(newSpecialization, selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a doctor to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDoctor() {
        int selectedRow = doctorsTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        // Go back to the main menu
        this.dispose();
        new MainMenuView("admin"); // Pass the role of the user (admin)
    }
}
