package controller;

import model.Doctor;
import model.UserFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    public void addDoctor(String name, String specialty, String contact, String password, String login) {
        Doctor doctor = UserFactory.createDoctor(name, contact, password, login, specialty);

        if (doctor != null) {
            System.out.println("Doctor added: " + doctor.getName());
        } else {
            System.out.println("Failed to add doctor.");
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false"
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Doctor");

            while (rs.next()) {
                int id = rs.getInt("UserID");
                String name = rs.getString("DoctorName");
                String specialization = rs.getString("Specialization");

                Doctor doctor = new Doctor(id, name, specialization, "N/A", "", "", "Doctor");
                doctors.add(doctor);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public void displayDoctors() {
        List<Doctor> doctors = getAllDoctors();
        for (Doctor doctor : doctors) {
            System.out.println("Doctor: " + doctor.getName() + ", Specialty: " + doctor.getSpecialty());
        }
    }
}