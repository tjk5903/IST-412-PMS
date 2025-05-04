package view;

import model.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class ViewLogsView extends JFrame {
    private JTable logTable;
    private DefaultTableModel tableModel;
    private JButton exportButton, backButton;
    private Admin loggedInAdmin;
    private String userRole;

    public ViewLogsView(String userRole, Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        this.userRole = userRole;

        setTitle("System Logs");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"LogID", "UserID", "Username", "DateOccurred", "EventLogged"}, 0);
        logTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(logTable);
        add(scrollPane, BorderLayout.CENTER);

        loadLogsFromDatabase();

        JPanel buttonPanel = new JPanel();
        exportButton = new JButton("Export as CSV");
        backButton = new JButton("Back to Main Menu");

        exportButton.addActionListener(e -> exportLogs());
        backButton.addActionListener(e -> goBackToMainMenu());

        buttonPanel.add(exportButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadLogsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AdminLogs ORDER BY ID DESC");

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ID"));
                row.add(rs.getInt("UserID"));
                row.add(rs.getString("UserName"));
                row.add(rs.getString("DateOccurred"));
                row.add(rs.getString("EventLogged"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading logs.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportLogs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Logs as CSV");
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileChooser.getSelectedFile() + ".csv"))) {
                Connection conn = DriverManager.getConnection("jdbc:ucanaccess://IST412PMSsystem/src/healthPlusDatabase1.accdb");
                //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthPlusDB?user=root&password=root123&useSSL=false");
                String eventLogged = loggedInAdmin.getLogin() + " downloaded logs";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                Statement st = conn.createStatement();
                String sql = "INSERT INTO AdminLogs (UserID, UserName, DateOccurred, EventLogged) VALUES (" +
                        loggedInAdmin.getUserID() + ", '" +
                        loggedInAdmin.getLogin().replace("'", "''") + "', '" +
                        formattedDateTime.replace("'", "''") + "', '" +
                        eventLogged.replace("'", "''") + "')";
                st.executeUpdate(sql);
                conn.close();
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    pw.print(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        pw.print(tableModel.getValueAt(row, col));
                        if (col < tableModel.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                }

                JOptionPane.showMessageDialog(this, "Logs exported successfully.");
                dispose();
                new ViewLogsView(userRole, loggedInAdmin);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error exporting logs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void goBackToMainMenu() {
        dispose();
        new MainMenuView(userRole, loggedInAdmin);
    }
}
