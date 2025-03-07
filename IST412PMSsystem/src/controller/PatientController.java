package controller;

import model.Patient;
import view.PatientView;

public class PatientController {

    private PatientView patientView;

    public PatientController(PatientView patientView) {
        this.patientView = patientView;
    }

    // Adds a new patient
    public void addPatient(int patientID, String name, int age, String gender, String contact, String address) {
        Patient patient = new Patient(patientID, name, age, gender, contact, address, null);
        Patient.addPatientToList(patient);
        System.out.println("Patient added: " + name);
    }

    // Displays all patients
    public void displayPatients() {
        Patient[] patients = Patient.getAllPatients();
        for (Patient patient : patients) {
            System.out.println("Patient: " + patient.getName() + ", Age: " + patient.getAge());
        }
    }
}
