package model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private int patientID;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String address;
    private List<String> medicalHistory;

    // Constructor
    public Patient(int patientID, String name, int age, String gender, String contact, String address, List<String> medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.role = "Patient";  // Set the role explicitly
    }

    @Override
    public String getUserInfo() {
        return "Patient: " + name + ", Age: " + age + ", Gender: " + gender + ", Contact: " + contact + ", Address: " + address + ", Medical History: " + medicalHistory;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    // Getters and Setters
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    // Static Methods to Manage Patient List
    public static void addPatientToList(Patient patient) {
        patientList.add(patient);
    }

    public static Patient[] getAllPatients() {
        return patientList.toArray(new Patient[0]);
    }

    // Static List of Patients
    private static List<Patient> patientList = new ArrayList<>();
}
