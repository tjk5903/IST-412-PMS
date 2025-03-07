package controller;

import model.Doctor;
import model.Patient;
import view.AdminView;

public class AdminController {

    private AdminView adminView;
    private DoctorController doctorController;
    private PatientController patientController;

    public AdminController(AdminView adminView, DoctorController doctorController, PatientController patientController) {
        this.adminView = adminView;
        this.doctorController = doctorController;
        this.patientController = patientController;
    }

    // Admin managing doctors
    public void manageDoctors() {
        doctorController.displayDoctors();  // Admin views doctors
    }

    // Admin managing patients
    public void managePatients() {
        patientController.displayPatients();  // Admin views patients
    }

    // Admin verifying insurance for a patient
    public void verifyInsurance(String patientID) {
        boolean isVerified = checkInsurance(patientID);
        adminView.displayInsuranceVerificationResult(isVerified, patientID);
    }

    // Simulate insurance check logic
    private boolean checkInsurance(String patientID) {
        return patientID.equals("P001");  // Mock logic: Insurance is verified for patient with ID P001
    }
}
