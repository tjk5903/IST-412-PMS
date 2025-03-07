package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderMedicationsView extends JFrame {
    private JComboBox<String> medicationComboBox;
    private JButton orderButton;
    private JTextField quantityField;

    public OrderMedicationsView() {
        setTitle("Order Medications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel medicationLabel = new JLabel("Select Medication:");
        add(medicationLabel);

        // Example list of medications - can be dynamically populated
        String[] medications = {"Aspirin", "Ibuprofen", "Paracetamol", "Amoxicillin"};
        medicationComboBox = new JComboBox<>(medications);
        add(medicationComboBox);

        JLabel quantityLabel = new JLabel("Enter Quantity:");
        add(quantityLabel);

        quantityField = new JTextField();
        add(quantityField);

        orderButton = new JButton("Order Medication");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderMedication();
            }
        });
        add(orderButton);

        setVisible(true);
    }

    private void orderMedication() {
        String medication = (String) medicationComboBox.getSelectedItem();
        String quantity = quantityField.getText();

        if (quantity.isEmpty() || !isNumeric(quantity)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Ordering " + quantity + " units of " + medication);
            // Here, you'd implement the actual logic to order the medication (e.g., call a backend service).
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
