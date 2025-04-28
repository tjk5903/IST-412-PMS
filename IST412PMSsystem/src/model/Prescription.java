package model;

import java.util.Date;
import java.util.Objects;

public class Prescription {
    private int prescriptionID;
    private int patientID;
    private int doctorID;
    private String medicationName;
    private String dosage;
    private Date dateIssued;
    private String status;

    public Prescription(int prescriptionID, int patientID, int doctorID, String medicationName, String dosage, Date dateIssued, String status) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.dateIssued = dateIssued;
        this.status = status;
    }


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
        return status;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public void setMedicationName(String medicationName) {
        if (medicationName != null && !medicationName.trim().isEmpty()) {
            this.medicationName = medicationName;
        } else {
            System.out.println("Invalid medication name.");
        }
    }

    public void setDosage(String dosage) {
        if (dosage != null && !dosage.trim().isEmpty()) {
            this.dosage = dosage;
        } else {
            System.out.println("Invalid dosage.");
        }
    }

    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Approved") || status.equals("Rejected")) {
            this.status = status;
        } else {
            System.out.println("Invalid status. Must be 'Pending', 'Approved', or 'Rejected'.");
        }
    }

    public void updateStatus(String action) {
        if (action.equalsIgnoreCase("Approve")) {
            this.status = "Approved";
        } else if (action.equalsIgnoreCase("Reject")) {
            this.status = "Rejected";
        } else {
            System.out.println("Invalid action. Must be 'Approve' or 'Reject'.");
        }
    }

    public void displayPrescriptionDetails() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Medication: " + medicationName);
        System.out.println("Dosage: " + dosage);
        System.out.println("Date Issued: " + dateIssued);
        System.out.println("Status: " + status);
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
