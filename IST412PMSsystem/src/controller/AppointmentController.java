package controller;

import model.Appointment;
import model.Observer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class AppointmentController {

    private static List<Appointment> appointments = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment getAppointmentById(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }

    public void scheduleAppointment(Appointment appointment) {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement st = conn.createStatement();
            String sql = "INSERT INTO Appointments (PatientID, DoctorName, AptDate, AptTime, Status) VALUES (" +
                    appointment.getPatient().getUserID() + ", '" +
                    appointment.getDoctor().getName().replace("'", "''") + "', #" +
                    appointment.getDate() + "#, '" +
                    appointment.getTime() + "', '" +
                    appointment.getStatus().replace("'", "''") + "')";
            st.executeUpdate(sql);
            Statement st1 = conn.createStatement();
            ResultSet doctorLogin = st1.executeQuery("SELECT UserLogin FROM User WHERE UserName = '" + appointment.getDoctor().getName().replace("'", "''") + "'");
            String name = "unknown";
            if (doctorLogin.next()) {
                name = doctorLogin.getString("UserLogin");
            }
            String eventLogged = appointment.getPatient().getLogin() + " scheduled appointment on "
                    + appointment.getDate() + " at " + appointment.getTime() + " with " + name;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Statement st2 = conn.createStatement();
            String sql2 = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                    appointment.getPatient().getUserID() + ", '" +
                    appointment.getPatient().getLogin().replace("'", "''") + "', '" +
                    formattedDateTime.replace("'", "''") + "', '" +
                    eventLogged.replace("'", "''") + "')";
            st2.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
