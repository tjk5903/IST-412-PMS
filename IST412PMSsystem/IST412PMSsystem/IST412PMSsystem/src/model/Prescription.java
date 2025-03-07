package model;

import java.util.Date;

/**
 * Represents a medical prescription given to a patient by a doctor.
 */
public class Prescription {
    private int prescriptionID;   // Unique identifier for the prescription
    private int patientID;        // ID of the patient receiving the prescription
    private int doctorID;         // ID of the doctor issuing the prescription
    private String medicationName; // Name of the prescribed medication
    private String dosage;        // Dosage instructions for the medication
    private Date dateIssued;      // Date the prescription was issued
    private String status;        // Status of the prescription (e.g., Pending, Approved)

    /**
     * Constructs a Prescription object.
     *
     * @param prescriptionID The unique identifier for the prescription.
     * @param patientID The ID of the patient receiving the prescription.
     * @param doctorID The ID of the doctor issuing the prescription.
     * @param medicationName The name of the prescribed medication.
     * @param dosage The dosage instructions for the medication.
     * @param dateIssued The date the prescription was issued.
     * @param status The status of the prescription.
     */
    public Prescription(int prescriptionID, int patientID, int doctorID, String medicationName, String dosage, Date dateIssued, String status) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.dateIssued = dateIssued;
        this.status = status; // Initialize status here
    }

    /**
     * Gets the prescription ID.
     *
     * @return The unique identifier of the prescription.
     */
    public int getPrescriptionID() {
        return prescriptionID;
    }

    /**
     * Gets the patient ID.
     *
     * @return The ID of the patient receiving the prescription.
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The ID of the doctor issuing the prescription.
     */
    public int getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the name of the prescribed medication.
     *
     * @return The name of the prescribed medication.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Gets the dosage instructions.
     *
     * @return The dosage instructions for the medication.
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Gets the date the prescription was issued.
     *
     * @return The issue date of the prescription.
     */
    public Date getDateIssued() {
        return dateIssued;
    }

    /**
     * Gets the status of the prescription.
     *
     * @return The status of the prescription.
     */
    public String getStatus() {
        return status; // Return the prescription status
    }

    /**
     * Sets the prescribed medication.
     *
     * @param medicationName The new medication name.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName; // Update the medication name
    }

    /**
     * Sets the dosage instructions.
     *
     * @param dosage The new dosage instructions.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage; // Update the dosage instructions
    }

    /**
     * Sets the status of the prescription.
     *
     * @param status The new status of the prescription.
     */
    public void setStatus(String status) {
        this.status = status; // Update the prescription status
    }

    /**
     * Displays prescription details.
     */
    public void displayPrescriptionDetails() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Medication: " + medicationName);
        System.out.println("Dosage: " + dosage);
        System.out.println("Date Issued: " + dateIssued);
        System.out.println("Status: " + status); // Display prescription status
    }
}
