package controller;

import model.Prescription;

import java.util.*;

/**
 * Controller for managing prescription requests and operations.
 */
public class PrescriptionController {
    private static Map<Integer, Prescription> pendingPrescriptions = new HashMap<>(); // Changed to Map for faster lookup
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
        pendingPrescriptions.put(prescription.getPrescriptionID(), prescription);
    }

    /**
     * Retrieves a list of all prescriptions for a specific patient.
     *
     * @param patientID The ID of the patient whose prescriptions are to be retrieved.
     * @return A list of prescriptions associated with the patient.
     */
    public static List<Prescription> getPatientPrescriptions(int patientID) {
        List<Prescription> prescriptions = new ArrayList<>();
        for (Prescription p : pendingPrescriptions.values()) {
            if (p.getPatientID() == patientID) {
                prescriptions.add(p);
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
        return new ArrayList<>(pendingPrescriptions.values());
    }

    /**
     * Updates the status of a prescription.
     *
     * @param prescriptionID The ID of the prescription.
     * @param newStatus      The new status of the prescription.
     */
    public static void updatePrescriptionStatus(int prescriptionID, String newStatus) {
        Prescription prescription = pendingPrescriptions.get(prescriptionID);
        if (prescription != null) {
            prescription.setStatus(newStatus);
        }
    }

    /**
     * Retrieves a prescription by its ID.
     *
     * @param prescriptionID The ID of the prescription.
     * @return The prescription object if found, otherwise null.
     */
    public static Prescription getPrescriptionById(int prescriptionID) {
        return pendingPrescriptions.get(prescriptionID);
    }
}
