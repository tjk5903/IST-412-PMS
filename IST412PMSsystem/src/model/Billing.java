package model;

/**
 * Represents the billing system for a patient in the Patient Management System.
 * Handles generating bills and managing payment information.
 */
public class Billing {

    private int billID;
    private Patient patient;
    private double amount;
    private String paymentStatus;

    /**
     * Constructs a new Billing object.
     *
     * @param billID The unique identifier for the bill.
     * @param patient The patient for whom the bill is being generated.
     * @param amount The total amount for the bill.
     * @param paymentStatus The status of the payment (e.g., paid, unpaid).
     */
    public Billing(int billID, Patient patient, double amount, String paymentStatus) {
        this.billID = billID;
        this.patient = patient;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
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

    // Other getters and setters.
}
