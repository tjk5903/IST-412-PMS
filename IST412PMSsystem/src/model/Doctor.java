package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int doctorID;
    private String name;
    private String specialty;
    private String contact;
    private static List<Doctor> doctorList = new ArrayList<>();

    public Doctor(int doctorID, String name, String specialty, String contact, List<String> availableTimes) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.contact = contact;
    }

    public static void addDoctorToList(Doctor doctor) {
        doctorList.add(doctor);
    }

    public static Doctor[] getAllDoctors() {
        return doctorList.toArray(new Doctor[0]);
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }
}
