package model;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int patientID;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String address;
    private static List<Patient> patientList = new ArrayList<>();

    public Patient(int patientID, String name, int age, String gender, String contact, String address, List<String> medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
    }

    public static void addPatientToList(Patient patient) {
        patientList.add(patient);
    }

    public static Patient[] getAllPatients() {
        return patientList.toArray(new Patient[0]);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
