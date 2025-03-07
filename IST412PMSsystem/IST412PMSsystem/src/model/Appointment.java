package model;


import java.util.ArrayList;
import java.util.List;

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
    private static List<Appointment> appointmentList = new ArrayList<>();

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

    /**
     * Checks if an appointment can be scheduled without conflicts.
     * Ensures the doctor is not double-booked at the same time.
     *
     * @return True if the appointment is successfully scheduled, false otherwise.
     */
    public boolean scheduleAppointment() {
        for (Appointment appt : appointmentList) {
            if (appt.getDoctor().equals(this.doctor) && appt.getDate().equals(this.date) && appt.getTime().equals(this.time)) {
                return false; // Doctor is already booked at this time
            }
        }
        appointmentList.add(this);
        return true;
    }

    /**
     * Retrieves all appointments for a given doctor.
     *
     * @param doctor The doctor whose appointments are to be retrieved.
     * @return A list of appointments associated with the doctor.
     */
    public static List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (appt.getDoctor().equals(doctor)) {
                result.add(appt);
            }
        }
        return result;
    }

    /**
     * Retrieves all appointments for a given patient.
     *
     * @param patient The patient whose appointments are to be retrieved.
     * @return A list of appointments associated with the patient.
     */
    public static List<Appointment> getAppointmentsByPatient(Patient patient) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (appt.getPatient().equals(patient)) {
                result.add(appt);
            }
        }
        return result;
    }

    /**
     * Retrieves the patient associated with the appointment.
     *
     * @return The patient.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient for the appointment.
     *
     * @param patient The patient to associate with the appointment.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Retrieves the doctor associated with the appointment.
     *
     * @return The doctor.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Sets the doctor for the appointment.
     *
     * @param doctor The doctor to associate with the appointment.
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Retrieves the appointment date.
     *
     * @return The date of the appointment.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for the appointment.
     *
     * @param date The new date for the appointment.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the appointment time.
     *
     * @return The time of the appointment.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time for the appointment.
     *
     * @param time The new time for the appointment.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Retrieves the status of the appointment.
     *
     * @return The appointment status.
     */
    public String getStatus() {
        return status;
    }
}
