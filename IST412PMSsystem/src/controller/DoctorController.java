package controller;

import model.Prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing doctor operations related to prescriptions.
 */
public class DoctorController {
    private static List<Prescription> approvedPrescriptions = new ArrayList<>();

    /**
     * Retrieves a list of all pending prescriptions.
     *
     * @return A list of strings representing pending prescriptions.
     */
    public static List<String> getPendingPrescriptions() {
        List<String> pending = new ArrayList<>();
        for (Prescription p : PrescriptionController.getPendingPrescriptions()) {
            if (p.getStatus().equals("Pending Approval")) {
                pending.add(p.getMedicationName()); // Ensure using getMedication() if that method exists
            }
        }
        return pending;
    }

    /**
     * Approves a prescription by its medication name.
     *
     * @param medicationName The name of the medication to approve.
     */
    public static void approvePrescription(String medicationName) {
        List<Prescription> pendingPrescriptions = PrescriptionController.getPendingPrescriptions();

        for (Prescription p : pendingPrescriptions) {
            if (p.getMedicationName().equals(medicationName)) {
                p.setStatus("Approved"); // Approve the prescription
                approvedPrescriptions.add(p); // Add to approved list
                break;
            }
        }
    }

    /**
     * Rejects a prescription by its medication name.
     *
     * @param medicationName The name of the medication to reject.
     */
    public static void rejectPrescription(String medicationName) {
        PrescriptionController.updatePrescriptionStatus(medicationName, "Rejected");
    }

    /**
     * Retrieves a list of all approved prescriptions.
     *
     * @return A list of approved prescriptions.
     */
    public static List<Prescription> getApprovedPrescriptions() {
        return approvedPrescriptions;
    }
}
