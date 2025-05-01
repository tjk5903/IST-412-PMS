package view;

import model.Admin;
import model.Patient;
import model.Doctor;
import view.LoginView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainMenuView extends JFrame {
    private String userRole;
    private Patient loggedInPatient;
    private Doctor loggedInDoctor;
    private Admin loggedInAdmin;

    public MainMenuView(String role, Patient loggedInPatient) {
        this.userRole = role;
        this.loggedInPatient = loggedInPatient;
        this.loggedInDoctor = null;
        this.loggedInAdmin = null;
        setupUI();
    }

    public MainMenuView(String role, Doctor doctor) {
        this.userRole = role;
        this.loggedInPatient = null;
        this.loggedInAdmin = null;
        this.loggedInDoctor = doctor;
        setupUI();
    }

    public MainMenuView(String role, Admin admin) {
        this.userRole = role;
        this.loggedInPatient = null;
        this.loggedInDoctor = null;
        this.loggedInAdmin = admin;
        setupUI();
    }

    private void setupUI() {
        setTitle("Main Menu - " + userRole.toUpperCase());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        if (loggedInPatient != null){
            JLabel welcomeLabel = new JLabel("Welcome, " + loggedInPatient.getName(), SwingConstants.CENTER);
            add(welcomeLabel);
        }
        else if (loggedInDoctor != null){
            JLabel welcomeLabel = new JLabel("Welcome, " + loggedInDoctor.getName(), SwingConstants.CENTER);
            add(welcomeLabel);
        }
        else{
            JLabel welcomeLabel = new JLabel("Welcome, " + loggedInAdmin.getName(), SwingConstants.CENTER);
            add(welcomeLabel);
        }

        setupRoleBasedButtons();

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            try {
                logout();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
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
                addButton("View Logs", e -> openView("ViewLogs"));
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
                new ViewPatientRecordsView(userRole, loggedInDoctor);
                break;
            case "PrescribeMedication":
                new PrescribeMedicationView(userRole, loggedInDoctor);
                break;
            case "ManageDoctors":
                new ManageDoctorsView(userRole, loggedInAdmin);
                break;
            case "ManagePatients":
                new ManagePatientsView(userRole, loggedInAdmin);
                break;
            case "ViewLogs":
                new ViewLogsView(userRole, loggedInAdmin);
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
            default:
                JOptionPane.showMessageDialog(this, "View not found!", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        dispose();
    }

    private void logout() throws SQLException {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
        String eventLogged = "";
        if (loggedInPatient != null) {
            eventLogged = loggedInPatient.getLogin() + " logout";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Statement st = conn.createStatement();
            String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                    loggedInPatient.getUserID() + ", '" +
                    loggedInPatient.getLogin().replace("'", "''") + "', '" +
                    formattedDateTime.replace("'", "''") + "', '" +
                    eventLogged.replace("'", "''") + "')";
            st.executeUpdate(sql);
        }
        else if (loggedInDoctor != null){
            eventLogged = loggedInDoctor.getLogin() + " logout";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Statement st = conn.createStatement();
            String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                    loggedInDoctor.getUserID() + ", '" +
                    loggedInDoctor.getLogin().replace("'", "''") + "', '" +
                    formattedDateTime.replace("'", "''") + "', '" +
                    eventLogged.replace("'", "''") + "')";
            st.executeUpdate(sql);
        }
        else{
            eventLogged = loggedInAdmin.getLogin() + " logout";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Statement st = conn.createStatement();
            String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                    loggedInAdmin.getUserID() + ", '" +
                    loggedInAdmin.getLogin().replace("'", "''") + "', '" +
                    formattedDateTime.replace("'", "''") + "', '" +
                    eventLogged.replace("'", "''") + "')";
            st.executeUpdate(sql);
        }

        conn.close();
        new LoginView();
    }
}
