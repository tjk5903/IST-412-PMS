package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private int doctorID;
    private String name;
    private String specialty;
    private String contact;
    private List<String> availableTimes;

    public Doctor(int doctorID, String name, String specialty, String contact, List<String> availableTimes) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.contact = contact;
        this.availableTimes = availableTimes;
        this.role = "Doctor";  // Set the role explicitly
    }

    @Override
    public String getUserInfo() {
        return "Doctor: " + name + ", Specialty: " + specialty + ", Contact: " + contact + ", Available Times: " + availableTimes;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    // Static Methods to Manage Doctor List
    public static void addDoctorToList(Doctor doctor) {
        doctorList.add(doctor);
    }

    public static Doctor[] getAllDoctors() {
        return doctorList.toArray(new Doctor[0]);
    }

    // Static List of Doctors
    private static List<Doctor> doctorList = new ArrayList<>();
}
