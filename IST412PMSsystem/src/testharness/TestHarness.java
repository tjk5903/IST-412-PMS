package testharness;

import controller.DoctorController;
import controller.PatientController;

public class TestHarness {

    public static void main(String[] args) {
        // Set up controllers (no views required)
        DoctorController doctorController = new DoctorController();
        PatientController patientController = new PatientController();

        // Test: Add Doctors and View them
        System.out.println("Test 1: Adding and viewing doctors from DB");
        doctorController.addDoctor("Dr. Smith", "Cardiology", "drsmith@example.com", "pass123", "drsmithlogin");
        doctorController.addDoctor("Dr. Lee", "Pediatrics", "drlee@example.com", "pass456", "drleelogin");
        doctorController.displayDoctors();

        // Test: Add Patients and View them
        System.out.println("\nTest 2: Adding and viewing patients from DB");
        patientController.addPatient("John Doe", "johnd@example.com", "pass123", "johnlogin", 30, "Flu");
        patientController.addPatient("Jane Smith", "janes@example.com", "pass456", "janelogin", 45, "Diabetes");
        patientController.displayPatients();
    }
}