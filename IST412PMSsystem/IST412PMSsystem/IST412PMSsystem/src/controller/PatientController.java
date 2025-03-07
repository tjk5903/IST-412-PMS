package controller;

import model.Patient;
import java.util.List;

/**
 * Controller for managing patient-related operations.
 */
public class PatientController {

    /**
     * Retrieves all registered patients.
     *
     * @return A list of all patients.
     */
    public List<Patient> getAllPatients() {
        return null; // Implementation here
    }

    /**
     * Retrieves a patient by their unique ID.
     *
     * @param patientID The ID of the patient.
     * @return The Patient object if found, otherwise null.
     */
    public Patient getPatientById(int patientID) {
        return null; // Implementation here
    }

    /**
     * Adds a new patient to the system.
     *
     * @param patient The patient object to be added.
     * @return true if the patient is successfully added, false otherwise.
     */
    public boolean addPatient(Patient patient) {
        return false; // Implementation here
    }

    /**
     * Updates a patient's details.
     *
     * @param patientID The ID of the patient.
     * @param updatedPatient The updated patient details.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updatePatient(int patientID, Patient updatedPatient) {
        return false; // Implementation here
    }

    /**
     * Deletes a patient record.
     *
     * @param patientID The ID of the patient to be deleted.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deletePatient(int patientID) {
        return false; // Implementation here
    }
}
