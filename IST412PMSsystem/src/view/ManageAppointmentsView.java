package view;

import controller.AppointmentController;
import model.Patient;
import model.Doctor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ManageAppointmentsView extends JFrame {
    private String userRole;
    private int userId;
    private String doctorName;
    private Doctor loggedInDoctor;
    private Patient loggedInPatient;
    private DefaultTableModel model;
    private JTable table;

    public ManageAppointmentsView(String userRole, Patient patient) {
        this.userRole = userRole;
        this.userId = patient.getUserID();
        this.loggedInPatient = patient;
        this.doctorName = null;
        initUI();
    }

    public ManageAppointmentsView(String userRole, Doctor doctor) {
        this.userRole = userRole;
        this.userId = -1;
        this.loggedInDoctor = doctor;
        this.doctorName = doctor.getName();
        this.loggedInPatient = null;
        initUI();
    }

    public ManageAppointmentsView(String userRole) {
        this.userRole = userRole;
        this.userId = -1;
        this.doctorName = null;
        this.loggedInPatient = null;
        initUI();
    }

    private void initUI() {
        setTitle("Manage Appointments");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Patient Name", "Doctor", "Date", "Time", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton scheduleButton = new JButton("Schedule Appointment");
        scheduleButton.addActionListener(e -> scheduleAppointment());
        if (!userRole.equalsIgnoreCase("patient")) {
            scheduleButton.setEnabled(false);
        }
        buttonPanel.add(scheduleButton);

        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.addActionListener(e -> cancelAppointment());
        buttonPanel.add(cancelButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadAppointmentsBasedOnUser();
        setVisible(true);
    }

    private void loadAppointmentsBasedOnUser() {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement st = conn.createStatement();
            ResultSet rs;
            if (userRole.equalsIgnoreCase("patient")) {
                rs = st.executeQuery("SELECT * FROM Appointments WHERE PatientID = " + userId);
            } else if (userRole.equalsIgnoreCase("doctor")) {
                rs = st.executeQuery("SELECT * FROM Appointments WHERE DoctorName = '" + doctorName + "'");
            } else {
                rs = st.executeQuery("SELECT * FROM Appointments");
            }
            while (rs.next()) {
                int appointmentId = rs.getInt("ID");
                int patientId = rs.getInt("PatientID");
                String patientName = getPatientName(patientId);
                String doctor = rs.getString("DoctorName");
                Date date = rs.getDate("AptDate");
                String time = rs.getString("AptTime");
                String status = rs.getString("Status");
                model.addRow(new Object[]{appointmentId, patientName, doctor, date.toString(), time, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading appointments.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void scheduleAppointment() {
        if (loggedInPatient != null) {
            new AppointmentWizard(userRole, loggedInPatient);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Only patients can schedule new appointments.");
        }
    }

    private void cancelAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Object value = table.getValueAt(selectedRow, 0);
            int appointmentId = Integer.parseInt(value.toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel appointment #" + appointmentId + "?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (deleteAppointmentFromDatabase(appointmentId)) {
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Appointment cancelled.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to cancel appointment.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment first.");
        }
    }

    private boolean deleteAppointmentFromDatabase(int appointmentId) {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement st = conn.createStatement();
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            ResultSet appointmentInfo = stmt.executeQuery("SELECT * FROM Appointments WHERE ID = " + appointmentId);
            String aptDate = "unknown";
            String aptTime = "unknown";
            if (appointmentInfo.next()) {
                aptDate = appointmentInfo.getString("AptDate");
                aptTime = appointmentInfo.getString("AptTime");
            }
            String eventLogged = "";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            String sql = "";
            if (Objects.equals(userRole, "patient")){
                eventLogged = loggedInPatient.getLogin() + " cancelled appointment on " + aptDate + " at " + aptTime +
                        " (ID: " + appointmentId + ")";
                sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        loggedInPatient.getUserID() + ", '" +
                        loggedInPatient.getLogin().replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
            }
            else{
                eventLogged = loggedInDoctor.getLogin() + " cancelled appointment on " + aptDate + " at " + aptTime +
                        " (ID: " + appointmentId + ")";
                sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        loggedInDoctor.getUserID() + ", '" +
                        loggedInDoctor.getLogin().replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
            }
            st.executeUpdate(sql);
            int rowsDeleted = st.executeUpdate("DELETE FROM Appointments WHERE ID = " + appointmentId);
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getPatientName(int patientId) {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT PatientName FROM Patient WHERE PatientID = " + patientId);
            if (rs.next()) {
                return rs.getString("PatientName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private void goBackToMainMenu() {
        if (loggedInPatient != null) {
            new MainMenuView(userRole, loggedInPatient);
        }
        else {
            new MainMenuView(userRole, loggedInDoctor);
        }
        dispose();
    }
}
