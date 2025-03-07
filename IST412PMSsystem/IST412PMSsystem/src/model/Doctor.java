package model;

import java.util.ArrayList;
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
    private static List<Doctor> doctorList = new ArrayList<>();

    /**
     * Constructs a new Doctor object and adds it to the doctor list.
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
        this.availableTimes = new ArrayList<>(availableTimes);
        doctorList.add(this);
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
     * Gets the doctor's name.
     *
     * @return The name of the doctor.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the doctor's specialty.
     *
     * @return The specialty of the doctor.
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Gets the contact details of the doctor.
     *
     * @return The contact information.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Gets the list of available times for the doctor.
     *
     * @return A list of available time slots for appointments.
     */
    public List<String> getAvailableTimes() {
        return new ArrayList<>(availableTimes);
    }

    /**
     * Checks if a doctor is available at a specific time.
     *
     * @param time The time slot to check.
     * @return True if the doctor is available at the given time, otherwise false.
     */
    public boolean isAvailable(String time) {
        return availableTimes.contains(time);
    }

    /**
     * Adds a new available time slot for the doctor.
     *
     * @param time The time slot to add.
     */
    public void addAvailableTime(String time) {
        if (!availableTimes.contains(time)) {
            availableTimes.add(time);
        }
    }

    /**
     * Removes an available time slot from the doctor's schedule.
     *
     * @param time The time slot to remove.
     */
    public void removeAvailableTime(String time) {
        availableTimes.remove(time);
    }

    /**
     * Retrieves all doctors with a specific specialty.
     *
     * @param specialty The specialty to filter by.
     * @return A list of doctors with the specified specialty.
     */
    public static List<Doctor> getDoctorsBySpecialty(String specialty) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if (doctor.getSpecialty().equalsIgnoreCase(specialty)) {
                result.add(doctor);
            }
        }
        return result;
    }
}
