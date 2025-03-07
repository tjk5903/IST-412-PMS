package testharness;

import controller.AdminController;
import controller.DoctorController;
import controller.PatientController;
import view.AdminView;
import view.DoctorView;
import view.PatientView;
import model.Doctor;
import model.Patient;

public class TestHarness {

    public static void main(String[] args) {
        // Set up the views
        AdminView adminView = new AdminView();
        DoctorView doctorView = new DoctorView();
        PatientView patientView = new PatientView();

        // Set up the controllers
        DoctorController doctorController = new DoctorController(doctorView);
        PatientController patientController = new PatientController(patientView);
        AdminController adminController = new AdminController(adminView, doctorController, patientController);

        // Test: Add Doctors and View them
        System.out.println("Test 1: Admin adding and viewing doctors");
        doctorController.addDoctor(1, "Dr. Smith", "Cardiology", "555-1234");
        doctorController.addDoctor(2, "Dr. Lee", "Pediatrics", "555-5678");
        adminController.manageDoctors();  // Admin views doctors

        // Test: Add Patients and View them
        System.out.println("\nTest 2: Admin adding and viewing patients");
        patientController.addPatient(1, "John Doe", 30, "Male", "555-9876", "123 Main St.");
        patientController.addPatient(2, "Jane Smith", 40, "Female", "555-6543", "456 Elm St.");
        adminController.managePatients();  // Admin views patients

        // Test: Admin verifying insurance for a patient
        System.out.println("\nTest 3: Admin verifying insurance for patient P001");
        adminController.verifyInsurance("P001");  // Expected: Insurance verified

        // Test: Admin verifying insurance for a patient without insurance
        System.out.println("\nTest 4: Admin verifying insurance for patient P999");
        adminController.verifyInsurance("P999");  // Expected: Insurance not verified

    }
}
