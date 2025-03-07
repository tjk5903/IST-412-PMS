package model;

import java.util.Date;
import java.util.Objects;

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

    // Getters
    public int getPrescriptionID() {
        return prescriptionID;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public String getStatus() {
        return status; // Return the prescription status
    }

    // Setters
    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID; // Set the prescription ID
    }

    public void setMedicationName(String medicationName) {
        if (medicationName != null && !medicationName.trim().isEmpty()) {
            this.medicationName = medicationName; // Update the medication name
        } else {
            System.out.println("Invalid medication name.");
        }
    }

    public void setDosage(String dosage) {
        if (dosage != null && !dosage.trim().isEmpty()) {
            this.dosage = dosage; // Update the dosage instructions
        } else {
            System.out.println("Invalid dosage.");
        }
    }

    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Approved") || status.equals("Rejected")) {
            this.status = status; // Set the status if it's valid
        } else {
            System.out.println("Invalid status. Must be 'Pending', 'Approved', or 'Rejected'.");
        }
    }

    /**
     * Method to approve or reject the prescription.
     *
     * @param action The action to perform: "Approve" or "Reject"
     */
    public void updateStatus(String action) {
        if (action.equalsIgnoreCase("Approve")) {
            this.status = "Approved";
        } else if (action.equalsIgnoreCase("Reject")) {
            this.status = "Rejected";
        } else {
            System.out.println("Invalid action. Must be 'Approve' or 'Reject'.");
        }
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

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionID=" + prescriptionID +
                ", patientID=" + patientID +
                ", doctorID=" + doctorID +
                ", medicationName='" + medicationName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", dateIssued=" + dateIssued +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prescription that = (Prescription) obj;
        return prescriptionID == that.prescriptionID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionID);
    }
}
