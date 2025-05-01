package controller;

import model.Patient;
import model.UserFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientController {

    private final String DB_URL = "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false";

    public void addPatient(String name, String contact, String password, String login, int age, String disease) {
        Patient patient = UserFactory.createPatient(name, contact, password, login, age, disease);

        if (patient != null) {
            System.out.println("Patient added: " + patient.getName());
        } else {
            System.out.println("Failed to add patient.");
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Patient");

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String name = rs.getString("PatientName");
                int age = rs.getInt("Age");
                String disease = rs.getString("Disease");

                Patient patient = new Patient(userID, name, age, "Unknown", "N/A", "N/A", "N/A", "N/A", "Patient");
                patients.add(patient);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }

    public void displayPatients() {
        List<Patient> patients = getAllPatients();
        for (Patient patient : patients) {
            System.out.println("Patient: " + patient.getName() + ", Age: " + patient.getAge());
        }
    }
}