package model;

import java.util.List;

/**
 * Represents a doctor in the Patient Management System.
 * Contains information such as the doctor's name, specialty, and available time slots.
 */
public class Doctor {

    private int doctorID;
    private String name;
    private String specialty;
    private String contact;
    private List<String> availableTimes;

    /**
     * Constructs a new Doctor object.
     *
     * @param doctorID The unique identifier for the doctor.
     * @param name The name of the doctor.
     * @param specialty The specialty of the doctor.
     * @param contact The contact details of the doctor.
     * @param availableTimes The list of available time slots for appointments.
     */
    public Doctor(int doctorID, String name, String specialty, String contact, List<String> availableTimes) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.contact = contact;
        this.availableTimes = availableTimes;
    }

    /**
     * Gets the doctor's ID.
     *
     * @return The unique ID of the doctor.
     */
    public int getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the doctor's ID.
     *
     * @param doctorID The new doctor ID to be set.
     */
    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the list of available times for the doctor.
     *
     * @return A list of available time slots for appointments.
     */
    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    /**
     * Sets the doctor's available times.
     *
     * @param availableTimes A list of available times to be set.
     */
    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    // Other getters and setters.
}
