package controller;

import model.Prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing pharmacy operations related to prescriptions.
 */
public class PharmacyController {
    private static List<Prescription> processedPrescriptions = new ArrayList<>();

    /**
     * Retrieves a list of all approved prescriptions.
     *
     * @return A list of strings representing approved prescriptions.
     */
    public static List<String> getApprovedPrescriptions() {
        List<String> approved = new ArrayList<>();
        for (Prescription p : DoctorController.getApprovedPrescriptions()) {
            if (p.getStatus().equals("Approved")) {
                approved.add(p.getMedicationName()); // Use getMedicationName() instead of getMedication()
            }
        }
        return approved;
    }

    /**
     * Processes an approved prescription by its medication name.
     *
     * @param medicationName The name of the medication to process.
     */
    public static void processPrescription(String medicationName) {
        for (Prescription p : DoctorController.getApprovedPrescriptions()) {
            if (p.getMedicationName().equals(medicationName)) { // Use getMedicationName() here as well
                p.setStatus("Processed");
                processedPrescriptions.add(p);
                break;
            }
        }
    }
}
