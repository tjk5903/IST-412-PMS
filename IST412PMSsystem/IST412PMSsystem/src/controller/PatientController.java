package controller;

import model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing patient-related operations.
 */
public class PatientController {
    private static List<Patient> patients = new ArrayList<>();

    /**
     * Retrieves all registered patients.
     *
     * @return A list of all patients.
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients); // Return a copy to avoid modification of the original list
    }

    /**
     * Retrieves a patient by their unique ID.
     *
     * @param patientID The ID of the patient.
     * @return The Patient object if found, otherwise null.
     */
    public Patient getPatientById(int patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID() == patientID) {
                return patient;
            }
        }
        return null; // Patient not found
    }

    /**
     * Adds a new patient to the system.
     *
     * @param patient The patient object to be added.
     * @return true if the patient is successfully added, false otherwise.
     */
    public boolean addPatient(Patient patient) {
        if (patient == null || getPatientById(patient.getPatientID()) != null) {
            return false; // Patient already exists or invalid input
        }
        patients.add(patient); // Add the patient to the list
        return true;
    }

    /**
     * Updates a patient's details.
     *
     * @param patientID The ID of the patient.
     * @param updatedPatient The updated patient details.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updatePatient(int patientID, Patient updatedPatient) {
        Patient existingPatient = getPatientById(patientID);
        if (existingPatient == null || updatedPatient == null) {
            return false; // Patient not found or invalid input
        }

        // Update details
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setAge(updatedPatient.getAge());
        existingPatient.setAddress(updatedPatient.getAddress());
        // You can update other details here if needed
        return true;
    }

    /**
     * Deletes a patient record.
     *
     * @param patientID The ID of the patient to be deleted.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deletePatient(int patientID) {
        Patient existingPatient = getPatientById(patientID);
        if (existingPatient == null) {
            return false; // Patient not found
        }

        patients.remove(existingPatient); // Remove the patient from the list
        return true;
    }
}
