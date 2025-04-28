package controller;

import model.Appointment;
import model.Observer;

import javax.swing.*;
import java.sql.*;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
