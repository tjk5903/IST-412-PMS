package view;

import model.Patient;
import model.Doctor;
import view.LoginView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private String userRole;
    private Patient loggedInPatient;
    private Doctor loggedInDoctor;

    public MainMenuView(String role, Patient loggedInPatient) {
        this.userRole = role;
        this.loggedInPatient = loggedInPatient;
        this.loggedInDoctor = null;
        setupUI();
    }

    public MainMenuView(String role, Doctor doctor) {
        this.userRole = role;
        this.loggedInPatient = null;
        this.loggedInDoctor = doctor;
        setupUI();
    }

    public MainMenuView(String role) {
        this.userRole = role;
        this.loggedInPatient = null;
        this.loggedInDoctor = null;
        setupUI();
    }

    private void setupUI() {
        setTitle("Main Menu - " + userRole.toUpperCase());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        JLabel welcomeLabel = new JLabel("Welcome, " + userRole.toUpperCase(), SwingConstants.CENTER);
        add(welcomeLabel);

        setupRoleBasedButtons();

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        add(logoutButton);

        setVisible(true);
    }

    private void setupRoleBasedButtons() {
        switch (userRole.toLowerCase()) {
            case "doctor":
                addButton("View Patient Records", e -> openView("ViewPatientRecords"));
                addButton("Prescribe Medication", e -> openView("PrescribeMedication"));
                addButton("Manage Appointments", e -> openView("ManageAppointments"));
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
                new LoginView();
                break;
        }
    }

    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    private void openView(String viewName) {
        switch (viewName) {
            case "ViewPatientRecords":
                new ViewPatientRecordsView(userRole);
                break;
            case "PrescribeMedication":
                new PrescribeMedicationView(userRole);
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
                new ViewPrescriptionsView(userRole, loggedInPatient);
                break;
            case "ManageAppointments":
                if (userRole.equalsIgnoreCase("patient")) {
                    new ManageAppointmentsView(userRole, loggedInPatient);
                } else if (userRole.equalsIgnoreCase("doctor")) {
                    new ManageAppointmentsView(userRole, loggedInDoctor);
                } else {
                    new ManageAppointmentsView(userRole);
                }
                break;
            case "ProcessPrescriptions":
                new ProcessPrescriptionsView();
                break;
            case "OrderMedications":
                new OrderMedicationsView();
                break;
            default:
                JOptionPane.showMessageDialog(this, "View not found!", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        dispose();
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        new LoginView();
    }
}
