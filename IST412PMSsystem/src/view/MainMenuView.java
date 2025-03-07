package view;

import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private String userRole;

    // Constructor now accepts the role passed from the login view
    public MainMenuView(String role) {
        this.userRole = role;
        setTitle("Main Menu - " + role.toUpperCase());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1)); // Vertical layout

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + role.toUpperCase(), SwingConstants.CENTER);
        add(welcomeLabel);

        // Add role-based buttons
        setupRoleBasedButtons();

        // Logout button to return to the login view
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        add(logoutButton);

        setVisible(true);
    }

    // Set up role-based buttons based on the user role
    private void setupRoleBasedButtons() {
        switch (userRole.toLowerCase()) {
            case "doctor":
                addButton("View Patient Records", e -> openView("ViewPatientRecords"));
                addButton("Prescribe Medication", e -> openView("PrescribeMedication"));
                break;
            case "patient":
                addButton("View Prescriptions", e -> openView("ViewPrescriptions"));
                addButton("Manage Appointments", e -> openView("ManageAppointments"));
                break;
            case "pharmacist":
                addButton("Process Prescriptions", e -> openView("ProcessPrescriptions"));
                addButton("Order Medications", e -> openView("OrderMedications"));
                break;
            case "admin":
                addButton("Manage Doctors", e -> openView("ManageDoctors"));
                addButton("Manage Patients", e -> openView("ManagePatients"));
                addButton("Insurance Verification", e -> openView("InsuranceVerification"));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown Role!", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
                new LoginView(); // Return to login if role is invalid
                break;
        }
    }

    // Helper method to add buttons for each view
    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    // Open the corresponding view for the user role
    private void openView(String viewName) {
        // Close the current menu and open the requested view
        switch (viewName) {
            case "ViewPatientRecords":
                new ViewPatientRecordsView(userRole); // Open the Patient Records view
                break;
            case "PrescribeMedication":
                new PrescribeMedicationView(userRole); // Open the Prescribe Medication view
                break;
            case "ManageDoctors":
                new ManageDoctorsView();
                break;
            case "ManagePatients":
                new ManagePatientsView();
                break;
            case "InsuranceVerification":
                new InsuranceVerificationView();
                break;
            case "ViewPrescriptions":
                new ViewPrescriptionsView(userRole); // Open the View Prescriptions view for the patient
                break;
            case "ManageAppointments":
                new ManageAppointmentsView(userRole); // Open the Manage Appointments view
                break;
            case "ProcessPrescriptions":
                new ProcessPrescriptionsView(); // Open the Process Prescriptions view
                break;
            case "OrderMedications":
                new OrderMedicationsView(); // Open the Order Medications view
                break;
            default:
                JOptionPane.showMessageDialog(this, "View not found!", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        dispose(); // Close the current menu
    }


    // Logout and go back to the login screen
    private void logout() {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose(); // Close the current view
        new LoginView(); // Go back to login screen
    }
}
