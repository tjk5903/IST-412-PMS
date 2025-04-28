package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository {

    public PrescriptionRepository() {}

    public List<String[]> getAllPrescriptions() {
        List<String[]> prescriptions = new ArrayList<>();
        String sql = "SELECT prescription_id, medication, dosage, start_date, end_date FROM Prescription";
        try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
             // Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[5];
                row[0] = rs.getString("prescription_id");
                row[1] = rs.getString("medication");
                row[2] = rs.getString("dosage");
                row[3] = rs.getString("start_date");
                row[4] = rs.getString("end_date");
                prescriptions.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public List<String[]> getPrescriptionsByPatientId(int patientId) {
        List<String[]> prescriptions = new ArrayList<>();
        String sql = "SELECT ID, Medication, Dosage, Frequency FROM Prescription WHERE PatientID = " + patientId;
        try (Connection connection = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("ID"));
                row[1] = rs.getString("Medication");
                row[2] = rs.getString("Dosage");
                row[3] = rs.getString("Frequency");
                prescriptions.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prescriptions;
    }
}
