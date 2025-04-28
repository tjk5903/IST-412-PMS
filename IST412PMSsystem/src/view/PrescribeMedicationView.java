package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PrescribeMedicationView extends JFrame {
    private String userRole;
    private JComboBox<String> patientDropdown;
    private Map<String, Integer> patientNameToIdMap = new HashMap<>();

    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;

    public PrescribeMedicationView(String userRole) {
        this.userRole = userRole;
        setTitle("Prescribe Medication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel patientLabel = new JLabel("Select Patient: ");
        patientDropdown = new JComboBox<>();
        loadPatients();

        JLabel medicationLabel = new JLabel("Medication: ");
        medicationField = new JTextField();

        JLabel dosageLabel = new JLabel("Dosage: ");
        dosageField = new JTextField();

        JLabel frequencyLabel = new JLabel("Frequency: ");
        frequencyField = new JTextField();

        add(patientLabel);
        add(patientDropdown);
        add(medicationLabel);
        add(medicationField);
        add(dosageLabel);
        add(dosageField);
        add(frequencyLabel);
        add(frequencyField);

        JButton prescribeButton = new JButton("Prescribe Medication");
        prescribeButton.addActionListener(e -> prescribeMedication());
        add(prescribeButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton);

        setVisible(true);
    }

    private void prescribeMedication() {
        String selectedPatientName = (String) patientDropdown.getSelectedItem();
        Integer patientId = patientNameToIdMap.get(selectedPatientName);
        String medication = medicationField.getText();
        String dosage = dosageField.getText();
        String frequency = frequencyField.getText();
        if (patientId == null || medication.isEmpty() || dosage.isEmpty() || frequency.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
                Statement st = conn.createStatement();
                String sql = "INSERT INTO Prescription (PatientID, Medication, Dosage, Frequency) VALUES (" +
                        patientId + ", '" +
                        medication.replace("'", "''") + "', '" +
                        dosage.replace("'", "''") + "', '" +
                        frequency.replace("'", "''") + "')";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(this, "Medication prescribed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MainMenuView(userRole);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error prescribing medication.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadPatients() {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT PatientID, PatientName FROM Patient");
            while (rs.next()) {
                int id = rs.getInt("PatientID");
                String name = rs.getString("PatientName");
                patientDropdown.addItem(name);
                patientNameToIdMap.put(name, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patients.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole);
    }
}
