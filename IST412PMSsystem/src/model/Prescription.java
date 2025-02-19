package model;

import java.util.Date;

/**
 * Represents a medical prescription given to a patient by a doctor.
 */
public class Prescription {
    private int prescriptionID;
    private int patientID;
    private int doctorID;
    private String medication;
    private String dosage;
    private Date dateIssued;

    /**
     * Constructs a Prescription object.
     *
     * @param prescriptionID The unique identifier for the prescription.
     * @param patientID The ID of the patient receiving the prescription.
     * @param doctorID The ID of the doctor issuing the prescription.
     * @param medication The name of the prescribed medication.
     * @param dosage The dosage instructions for the medication.
     * @param dateIssued The date the prescription was issued.
     */
    public Prescription(int prescriptionID, int patientID, int doctorID, String medication, String dosage, Date dateIssued) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.medication = medication;
        this.dosage = dosage;
        this.dateIssued = dateIssued;
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
     * Gets the prescribed medication name.
     *
     * @return The name of the prescribed medication.
     */
    public String getMedication() {
        return medication;
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
     * Sets the prescribed medication.
     *
     * @param medication The new medication name.
     */
    public void setMedication(String medication) {
        this.medication = medication;
    }

    /**
     * Sets the dosage instructions.
     *
     * @param dosage The new dosage instructions.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Displays prescription details.
     */
    public void displayPrescriptionDetails() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Medication: " + medication);
        System.out.println("Dosage: " + dosage);
        System.out.println("Date Issued: " + dateIssued);
    }
}
