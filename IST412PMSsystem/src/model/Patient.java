package model;

import java.util.List;

/**
 * Represents a patient in the Patient Management System.
 * Contains personal information and medical history.
 */
public class Patient {

    private int patientID;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private List<String> medicalHistory;

    /**
     * Constructs a new Patient object.
     *
     * @param patientID The unique identifier for the patient.
     * @param name The name of the patient.
     * @param age The age of the patient.
     * @param gender The gender of the patient.
     * @param contact The contact details of the patient.
     * @param medicalHistory The medical history of the patient.
     */
    public Patient(int patientID, String name, int age, String gender, String contact, List<String> medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.medicalHistory = medicalHistory;
    }

    /**
     * Gets the patient's ID.
     *
     * @return The patient's unique ID.
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient's ID.
     *
     * @param patientID The new ID to be set.
     */
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Retrieves the patient's medical history.
     *
     * @return A list of medical conditions the patient has had.
     */
    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Updates the patient's medical history.
     *
     * @param medicalHistory A list of the new conditions to be updated.
     */
    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    // Other getters and setters can follow the same pattern.
}
