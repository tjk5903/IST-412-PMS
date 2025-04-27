package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository {
    private Connection connection;

    public PrescriptionRepository(Connection connection) {
        this.connection = connection;
    }

    public List<String[]> getAllPrescriptions() {
        List<String[]> prescriptions = new ArrayList<>();

        String sql = "SELECT prescription_id, medication, dosage, start_date, end_date FROM prescriptions";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
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
}
