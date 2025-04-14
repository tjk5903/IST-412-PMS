package model;

import java.sql.*;

public class UserFactory {

    public static Doctor createDoctor(String name, String contact, String password, String login, String specialization) {
        int newUserID = getNextAvailableUserID();
        Doctor doctor = null;

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");

            Statement st = conn.createStatement();
            String inUser = "INSERT INTO User (UserID, UserName, UserContact, UserPassword, UserLogin, UserRole) " +
                    "VALUES (" + newUserID + ", '" + name + "', '" + contact + "', '" +
                    password + "', '" + login + "', 'Doctor')";
            st.executeUpdate(inUser);

            String inDoctor = "INSERT INTO Doctor (DoctorID, DoctorName, Specialty) " +
                    "VALUES (" + newUserID + ", '" + name + "', '" + specialization + "')";
            st.executeUpdate(inDoctor);

            st.close();
            conn.close();
            doctor = new Doctor(newUserID, name, specialization, contact, password, login, "Doctor");

        } catch (Exception e) {
            System.out.println("Error creating doctor: " + e.getMessage());
            e.printStackTrace();
        }

        return doctor;
    }

    public static Patient createPatient(String name, String contact, String password, String login, int age, String disease) {
        int newUserID = getNextAvailableUserID();
        Patient patient = null;

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");

            Statement st = conn.createStatement();

            String inUser = "INSERT INTO User (UserID, UserName, UserContact, UserPassword, UserLogin, UserRole) " +
                    "VALUES (" + newUserID + ", '" + name + "', '" + contact + "', '" +
                    password + "', '" + login + "', 'Patient')";
            st.executeUpdate(inUser);

            String inPatient = "INSERT INTO Patient (PatientID, PatientName, Age, Disease) " +
                    "VALUES (" + newUserID + ", '" + name + "', " + age + ", '" + disease + "')";
            st.executeUpdate(inPatient);

            st.close();
            conn.close();

            patient = new Patient(newUserID, name, age, "Unknown", contact, "N/A", password, login, "Patient");
        } catch (Exception e) {
            System.out.println("Error creating patient: " + e.getMessage());
            e.printStackTrace();
        }

        return patient;
    }

    private static int getNextAvailableUserID() {
        int maxID = 0;
        try {
            //Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(UserID) AS MaxID FROM User");
            if (rs.next()) {
                maxID = rs.getInt("MaxID");
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error getting next UserID: " + e.getMessage());
        }
        return maxID + 1;
    }
}
