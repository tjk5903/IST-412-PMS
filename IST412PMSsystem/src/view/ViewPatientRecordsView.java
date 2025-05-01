package view;

import model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewPatientRecordsView extends JFrame {
    private String userRole;
    private JTable table;
    private DefaultTableModel model;

    private Doctor loggedInDoctor;

    public ViewPatientRecordsView(String userRole, Doctor loggedInDoctor) {
        this.userRole = userRole;
        this.loggedInDoctor = loggedInDoctor;
        setTitle("View Patient Records");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Patient ID", "Name", "Age", "Diagnosis", "Medication"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        buttonPanel.add(backButton);

        if (userRole.equalsIgnoreCase("admin") || userRole.equalsIgnoreCase("doctor")) {
            JButton editButton = new JButton("Edit Selected Record");
            editButton.addActionListener(e -> {
                try {
                    editSelectedRecord();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttonPanel.add(editButton);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        loadPatientsFromDatabase();

        setVisible(true);
    }


    private void editSelectedRecord() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.");
            return;
        }

        int patientId = (int) model.getValueAt(selectedRow, 0);
        String patientName = (String) model.getValueAt(selectedRow, 1);
        String currentDiagnosis = (String) model.getValueAt(selectedRow, 3);
        String currentMedication = (String) model.getValueAt(selectedRow, 4);

        JLabel idLabel = new JLabel(String.valueOf(patientId));
        JLabel nameLabel = new JLabel(patientName);

        JComboBox<String> diagnosisDropdown = new JComboBox<>();
        diagnosisDropdown.addItem("Flu");
        diagnosisDropdown.addItem("COVID-19");
        diagnosisDropdown.addItem("Cold");
        diagnosisDropdown.addItem("Strep");
        diagnosisDropdown.addItem("Pneumonia");
        diagnosisDropdown.addItem("Other");
        diagnosisDropdown.setSelectedItem(currentDiagnosis);

        JComboBox<String> medicationDropdown = new JComboBox<>();
        for (String med : currentMedication.split(",")) {
            medicationDropdown.addItem(med.trim());
        }

        JButton deleteMedicationButton = new JButton("Delete Selected Medication");

        deleteMedicationButton.addActionListener(e -> {
            String selectedMed = (String) medicationDropdown.getSelectedItem();
            if (selectedMed != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
                    Statement stmt = conn.createStatement();
                    String deleteSQL = "DELETE FROM Prescription WHERE PatientID = " + patientId +
                            " AND Medication = '" + selectedMed.replace("'", "''") + "'";
                    stmt.executeUpdate(deleteSQL);
                    medicationDropdown.removeItem(selectedMed);
                    JOptionPane.showMessageDialog(null, "Medication '" + selectedMed + "' deleted successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to delete medication.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient ID:"));
        panel.add(idLabel);
        panel.add(new JLabel("Name:"));
        panel.add(nameLabel);
        panel.add(new JLabel("Diagnosis:"));
        panel.add(diagnosisDropdown);
        panel.add(new JLabel("Medications:"));
        panel.add(medicationDropdown);
        panel.add(deleteMedicationButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Diagnosis and Medications", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
                Statement stmt = conn.createStatement();
                String newDiagnosis = (String) diagnosisDropdown.getSelectedItem();
                String updateDiagnosisSql = "UPDATE Patient SET Disease = '" + newDiagnosis + "' WHERE PatientID = " + patientId;
                stmt.executeUpdate(updateDiagnosisSql);
                model.setValueAt(newDiagnosis, selectedRow, 3);
                StringBuilder newMeds = new StringBuilder();
                for (int i = 0; i < medicationDropdown.getItemCount(); i++) {
                    if (i > 0) newMeds.append(", ");
                    newMeds.append(medicationDropdown.getItemAt(i));
                }
                model.setValueAt(newMeds.toString(), selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Diagnosis updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error during update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Statement st = conn.createStatement();
        ResultSet patientEdit = st.executeQuery("SELECT * FROM User WHERE ID = " + patientId);
        String loginName = "unknown";
        if (patientEdit.next()) {
            loginName = patientEdit.getString("UserLogin");
        }
        String eventLogged = loggedInDoctor.getLogin() + " edited " + loginName;
        String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                loggedInDoctor.getUserID() + ", '" +
                loggedInDoctor.getLogin().replace("'", "''") + "', '" +
                formattedDateTime.replace("'", "''") + "', '" +
                eventLogged.replace("'", "''") + "')";
        st.executeUpdate(sql);
        dispose();
        new ViewPatientRecordsView(userRole, loggedInDoctor);
    }

    private void loadPatientsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT PatientID, PatientName, Age, Disease FROM Patient");

            while (rs.next()) {
                int id = rs.getInt("PatientID");
                String name = rs.getString("PatientName");
                int age = rs.getInt("Age");
                String disease = rs.getString("Disease");
                ResultSet medications = stmt.executeQuery("SELECT medication FROM Prescription WHERE PatientID = " + id);
                StringBuilder meds = new StringBuilder();
                String medication = "";
                while (medications.next()) {
                    if (meds.length() > 0) meds.append(", ");
                    meds.append(medications.getString("Medication"));
                }
                medication = meds.length() > 0 ? meds.toString() : "N/A";
                model.addRow(new Object[]{id, name, age, disease, medication});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient records from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole, loggedInDoctor);
    }
}
