package controller;

import model.Doctor;
import view.DoctorView;

public class DoctorController {

    private DoctorView doctorView;

    public DoctorController(DoctorView doctorView) {
        this.doctorView = doctorView;
    }

    // Adds a new doctor
    public void addDoctor(int doctorID, String name, String specialty, String contact) {
        Doctor doctor = new Doctor(doctorID, name, specialty, contact, null);
        Doctor.addDoctorToList(doctor);
        System.out.println("Doctor added: " + name);
    }

    // Displays all doctors
    public void displayDoctors() {
        Doctor[] doctors = Doctor.getAllDoctors();
        for (Doctor doctor : doctors) {
            System.out.println("Doctor: " + doctor.getName() + ", Specialty: " + doctor.getSpecialty());
        }
    }
}
