package view;

import repository.PrescriptionRepository;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewPrescriptionsView extends JFrame {
    private String userRole;
    private Patient loggedInPatient;

    public ViewPrescriptionsView(String userRole, Patient loggedInPatient) {
        this.userRole = userRole;
        this.loggedInPatient = loggedInPatient;

        setTitle("View My Prescriptions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Prescription ID", "Medication", "Dosage", "Frequency"};

        PrescriptionRepository repo = new PrescriptionRepository();
        List<String[]> prescriptionList = repo.getPrescriptionsByPatientId(loggedInPatient.getUserID());

        Object[][] data = prescriptionList.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> goBackToMainMenu());
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole, loggedInPatient);
    }
}
