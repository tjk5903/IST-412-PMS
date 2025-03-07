package controller;

import model.Appointment;
import java.util.List;
import java.util.ArrayList;

/**
 * Controller for managing appointment-related operations.
 */
public class AppointmentController {

    // Simulating a database of appointments
    private static List<Appointment> appointments = new ArrayList<>();

    /**
     * Retrieves all scheduled appointments.
     *
     * @return A list of all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointments; // Returns the list of appointments
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentID The ID of the appointment.
     * @return The Appointment object if found, otherwise null.
     */
    public Appointment getAppointmentById(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment; // Returns the appointment if found
            }
        }
        return null; // Returns null if not found
    }

    /**
     * Schedules a new appointment.
     *
     * @param appointment The appointment object to be scheduled.
     * @return true if the appointment is successfully scheduled, false otherwise.
     */
    public boolean scheduleAppointment(Appointment appointment) {
        if (appointment != null) {
            appointments.add(appointment); // Adds the new appointment to the list
            return true; // Successfully scheduled
        }
        return false; // Failed to schedule
    }

    /**
     * Updates an existing appointment's details.
     *
     * @param appointmentID The ID of the appointment.
     * @param updatedAppointment The updated appointment details.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateAppointment(int appointmentID, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(appointmentID); // Find the existing appointment
        if (existingAppointment != null) {
            existingAppointment.setDate(updatedAppointment.getDate());
            existingAppointment.setTime(updatedAppointment.getTime());
            existingAppointment.setPatient(updatedAppointment.getPatient());
            existingAppointment.setDoctor(updatedAppointment.getDoctor());
            return true; // Successfully updated
        }
        return false; // Appointment not found, update failed
    }

    /**
     * Cancels an appointment.
     *
     * @param appointmentID The ID of the appointment to be canceled.
     * @return true if cancellation was successful, false otherwise.
     */
    public boolean cancelAppointment(int appointmentID) {
        Appointment appointmentToCancel = getAppointmentById(appointmentID);
        if (appointmentToCancel != null) {
            appointments.remove(appointmentToCancel); // Removes the appointment from the list
            return true; // Successfully canceled
        }
        return false; // Appointment not found, cancellation failed
    }
}
