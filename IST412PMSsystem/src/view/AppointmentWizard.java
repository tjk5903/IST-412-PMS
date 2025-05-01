package view;

import controller.AppointmentController;
import model.Appointment;
import model.Doctor;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppointmentWizard extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> doctorDropdown;
    private JComboBox<String> monthDropdown;
    private JComboBox<Integer> dayDropdown;
    private JComboBox<Integer> yearDropdown;
    private JComboBox<String> timeDropdown;

    private JButton backButton;
    private JButton scheduleButton;

    private Patient loggedInPatient;
    private String userRole;

    public AppointmentWizard(String userRole, Patient patient) {
        this.userRole = userRole;
        this.loggedInPatient = patient;

        setTitle("Schedule Appointment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new GridBagLayout());
        setContentPane(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel doctorLabel = new JLabel("Doctor:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(doctorLabel, gbc);

        doctorDropdown = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(doctorDropdown, gbc);

        populateDoctorDropdown();

        JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(dateLabel, gbc);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monthDropdown = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });
        dayDropdown = new JComboBox<>();
        yearDropdown = new JComboBox<>();

        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i <= currentYear + 2; i++) {
            yearDropdown.addItem(i);
        }

        datePanel.add(monthDropdown);
        datePanel.add(dayDropdown);
        datePanel.add(yearDropdown);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(datePanel, gbc);

        updateDaysDropdown();
        monthDropdown.addActionListener(e -> updateDaysDropdown());
        yearDropdown.addActionListener(e -> updateDaysDropdown());

        JLabel timeLabel = new JLabel("Time:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(timeLabel, gbc);

        timeDropdown = new JComboBox<>();
        populateTimeDropdown();
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(timeDropdown, gbc);

        backButton = new JButton("Back");
        scheduleButton = new JButton("Schedule");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(scheduleButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        backButton.addActionListener(e -> {
            new ManageAppointmentsView(userRole, loggedInPatient);
            dispose();
        });

        scheduleButton.addActionListener(e -> scheduleAppointment());
        setVisible(true);
    }

    private void populateDoctorDropdown() {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DoctorName FROM Doctor");
            while (rs.next()) {
                doctorDropdown.addItem(rs.getString("DoctorName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctors.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDaysDropdown() {
        int selectedMonthIndex = monthDropdown.getSelectedIndex();
        Integer selectedYear = (Integer) yearDropdown.getSelectedItem();
        if (selectedYear == null) return;

        int daysInMonth;
        switch (selectedMonthIndex) {
            case 1:
                daysInMonth = ((selectedYear % 4 == 0 && selectedYear % 100 != 0) || (selectedYear % 400 == 0)) ? 29 : 28;
                break;
            case 3: case 5: case 8: case 10:
                daysInMonth = 30;
                break;
            default:
                daysInMonth = 31;
        }

        Integer selectedDay = (Integer) dayDropdown.getSelectedItem();
        dayDropdown.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            dayDropdown.addItem(i);
        }
        if (selectedDay != null && selectedDay <= daysInMonth) {
            dayDropdown.setSelectedItem(selectedDay);
        } else {
            dayDropdown.setSelectedItem(1);
        }
    }

    private void populateTimeDropdown() {
        List<String> times = new ArrayList<>();
        for (int hour = 8; hour < 17; hour++) {
            times.add(formatTime(hour, 0));
            times.add(formatTime(hour, 30));
        }
        for (String t : times) {
            timeDropdown.addItem(t);
        }
    }

    private String formatTime(int hour24, int minute) {
        int hour12 = hour24 % 12 == 0 ? 12 : hour24 % 12;
        String ampm = (hour24 < 12) ? "AM" : "PM";
        return String.format("%d:%02d %s", hour12, minute, ampm);
    }

    private String convertTo24HourFormat(String timeWithAmPm) {
        try {
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("h:mm a");
            LocalTime time = LocalTime.parse(timeWithAmPm, parser);
            return time.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void scheduleAppointment() {
        String selectedDoctorName = (String) doctorDropdown.getSelectedItem();
        int day = (Integer) dayDropdown.getSelectedItem();
        int month = monthDropdown.getSelectedIndex() + 1;
        int year = (Integer) yearDropdown.getSelectedItem();
        String selectedTime = (String) timeDropdown.getSelectedItem();

        if (selectedDoctorName == null || selectedTime == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        LocalDate selectedDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalTime selectedTimeParsed = LocalTime.parse(convertTo24HourFormat(selectedTime));

        if (selectedDate.isBefore(today) || (selectedDate.isEqual(today) && selectedTimeParsed.isBefore(now))) {
            JOptionPane.showMessageDialog(this, "Cannot book past date or time.");
            return;
        }

        String dateString = String.format("%04d-%02d-%02d", year, month, day);
        String dbTime = convertTo24HourFormat(selectedTime);

        if (appointmentExists(selectedDoctorName, dateString, dbTime)) {
            JOptionPane.showMessageDialog(this, "This doctor already has an appointment at that time!");
            return;
        }

        Doctor doctor = new Doctor(0, selectedDoctorName, "General", "unknown@hospital.com", null, null, "Doctor");
        Appointment appointment = new Appointment(0, loggedInPatient, doctor, dateString, dbTime, "Scheduled");

        AppointmentController controller = new AppointmentController();
        controller.scheduleAppointment(appointment);

        JOptionPane.showMessageDialog(this, "Appointment scheduled!");
        new ManageAppointmentsView(userRole, loggedInPatient);
        dispose();
    }

    private boolean appointmentExists(String doctorName, String date, String time) {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Appointments WHERE DoctorName = '" + doctorName.replace("'", "''") +
                    "' AND AptDate = '" + date + "' AND AptTime = '" + time + "'";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
