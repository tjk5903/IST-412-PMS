package controller;

import model.Prescription;

import java.util.*;

public class PrescriptionController {
    private static Map<Integer, Prescription> pendingPrescriptions = new HashMap<>();
    private static int prescriptionCounter = 0;

    public static void requestPrescription(int patientID, int doctorID, String medicationName, String dosage) {
        Prescription prescription = new Prescription(++prescriptionCounter, patientID, doctorID, medicationName, dosage, new Date(), "Pending Approval");
        pendingPrescriptions.put(prescription.getPrescriptionID(), prescription);
    }

    public static List<Prescription> getPatientPrescriptions(int patientID) {
        List<Prescription> prescriptions = new ArrayList<>();
        for (Prescription p : pendingPrescriptions.values()) {
            if (p.getPatientID() == patientID) {
                prescriptions.add(p);
            }
        }
        return prescriptions;
    }

    public static List<Prescription> getPendingPrescriptions() {
        return new ArrayList<>(pendingPrescriptions.values());
    }

    public static void updatePrescriptionStatus(int prescriptionID, String newStatus) {
        Prescription prescription = pendingPrescriptions.get(prescriptionID);
        if (prescription != null) {
            prescription.setStatus(newStatus);
        }
    }

    public static Prescription getPrescriptionById(int prescriptionID) {
        return pendingPrescriptions.get(prescriptionID);
    }
}
