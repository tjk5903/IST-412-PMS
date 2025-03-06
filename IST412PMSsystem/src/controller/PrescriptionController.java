package controller;

import model.Prescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for managing prescription requests and operations.
 */
public class PrescriptionController {
    private static List<Prescription> pendingPrescriptions = new ArrayList<>();
    private static int prescriptionCounter = 0; // To keep track of prescription IDs

    /**
     * Requests a prescription for the specified medication.
     *
     * @param patientID      The ID of the patient receiving the prescription.
     * @param doctorID       The ID of the doctor issuing the prescription.
     * @param medicationName The name of the medication to be prescribed.
     * @param dosage         The dosage instructions for the medication.
     */
    public static void requestPrescription(int patientID, int doctorID, String medicationName, String dosage) {
        Prescription prescription = new Prescription(++prescriptionCounter, patientID, doctorID, medicationName, dosage, new Date(), "Pending Approval");
        pendingPrescriptions.add(prescription);
    }

    /**
     * Retrieves a list of all prescriptions for a specific patient.
     *
     * @param patientID The ID of the patient whose prescriptions are to be retrieved.
     * @return A list of strings representing the patient's prescriptions.
     */
    public static List<String> getPatientPrescriptions(int patientID) {
        List<String> prescriptions = new ArrayList<>();
        for (Prescription p : pendingPrescriptions) {
            if (p.getPatientID() == patientID) {
                prescriptions.add(p.getMedicationName() + " - " + p.getStatus());
            }
        }
        return prescriptions;
    }

    /**
     * Retrieves a list of all pending prescriptions.
     *
     * @return A list of pending prescriptions.
     */
    public static List<Prescription> getPendingPrescriptions() {
        return pendingPrescriptions;
    }

    /**
     * Updates the status of a prescription.
     *
     * @param medicationName The name of the medication.
     * @param newStatus      The new status of the prescription.
     */
    public static void updatePrescriptionStatus(String medicationName, String newStatus) {
        for (Prescription p : pendingPrescriptions) {
            if (p.getMedicationName().equals(medicationName)) {
                p.setStatus(newStatus);
                break;
            }
        }
    }
}
