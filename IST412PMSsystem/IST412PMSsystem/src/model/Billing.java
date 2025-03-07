package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the billing system for a patient in the Patient Management System.
 * Handles generating bills and managing payment information.
 */
public class Billing {
    private int billID;
    private Patient patient;
    private double amount;
    private String paymentStatus;
    private static List<Billing> billingList = new ArrayList<>();

    /**
     * Constructs a new Billing object and adds it to the billing list.
     *
     * @param billID The unique identifier for the bill.
     * @param patient The patient for whom the bill is being generated.
     * @param amount The total amount for the bill.
     * @param paymentStatus The status of the payment (e.g., Paid, Unpaid).
     */
    public Billing(int billID, Patient patient, double amount, String paymentStatus) {
        this.billID = billID;
        this.patient = patient;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        billingList.add(this);
    }

    /**
     * Gets the bill ID.
     *
     * @return The unique bill ID.
     */
    public int getBillID() {
        return billID;
    }

    /**
     * Gets the total amount of the bill.
     *
     * @return The amount for the bill.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the total amount for the bill.
     *
     * @param amount The new amount for the bill.
     */
    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        }
    }

    /**
     * Gets the payment status of the bill.
     *
     * @return The payment status (e.g., Paid, Unpaid).
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the bill.
     *
     * @param paymentStatus The new payment status.
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Confirms the payment for the bill.
     */
    public void confirmPayment() {
        this.paymentStatus = "Paid";
    }

    /**
     * Gets the patient associated with the bill.
     *
     * @return The patient associated with the bill.
     */
    public Patient getPatient() {
        return patient;
    }


    /**
     * Retrieves all bills for a given patient.
     *
     * @param patient The patient whose bills are to be retrieved.
     * @return A list of bills associated with the patient.
     */
    public static List<Billing> getBillsByPatient(Patient patient) {
        List<Billing> result = new ArrayList<>();
        for (Billing bill : billingList) {
            if (bill.getPatient().equals(patient)) {
                result.add(bill);
            }
        }
        return result;
    }

    /**
     * Retrieves all bills based on payment status (Paid/Unpaid).
     *
     * @param status The payment status to filter by.
     * @return A list of bills matching the specified payment status.
     */
    public static List<Billing> getBillsByStatus(String status) {
        List<Billing> result = new ArrayList<>();
        for (Billing bill : billingList) {
            if (bill.getPaymentStatus().equalsIgnoreCase(status)) {
                result.add(bill);
            }
        }
        return result;
    }
    /**
     * Retrieves all billing records.
     *
     * @return A list of all billing records.
     */
    public static List<Billing> getBillingList() {
        return billingList;
    }
}
