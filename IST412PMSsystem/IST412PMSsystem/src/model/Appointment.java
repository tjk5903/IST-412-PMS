// testing git

package model;

import model.Doctor;

/**
 * Represents an appointment in the Patient Management System.
 * Includes the date, time, patient, and doctor details.
 */
public class Appointment {

    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String status;

    /**
     * Constructs a new Appointment object.
     *
     * @param appointmentID The unique identifier for the appointment.
     * @param patient The patient attending the appointment.
     * @param doctor The doctor associated with the appointment.
     * @param date The date of the appointment.
     * @param time The time of the appointment.
     * @param status The current status of the appointment (e.g., confirmed, pending, cancelled).
     */
    public Appointment(int appointmentID, Patient patient, Doctor doctor, String date, String time, String status) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    /**
     * Gets the appointment ID.
     *
     * @return The unique ID for the appointment.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentID The new ID for the appointment.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Confirms the appointment.
     */
    public void confirmAppointment() {
        this.status = "Confirmed";
    }

    /**
     * Cancels the appointment.
     */
    public void cancelAppointment() {
        this.status = "Cancelled";
    }

    // Other getters and setters can follow.
}
