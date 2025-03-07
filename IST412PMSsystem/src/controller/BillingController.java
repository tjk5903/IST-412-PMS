package controller;

import model.Billing;
import java.util.List;

/**
 * Controller for managing billing and payments.
 */
public class BillingController {

    // Simulating a database of bills
    private static List<Billing> billingList = Billing.getBillingList();

    /**
     * Retrieves all bills.
     *
     * @return A list of all bills.
     */
    public List<Billing> getAllBills() {
        return billingList; // Returns the list of all bills
    }

    /**
     * Retrieves a bill by its ID.
     *
     * @param billID The ID of the bill.
     * @return The Bill object if found, otherwise null.
     */
    public Billing getBillById(int billID) {
        for (Billing bill : billingList) {
            if (bill.getBillID() == billID) {
                return bill; // Returns the bill if found
            }
        }
        return null; // Returns null if not found
    }

    /**
     * Generates a new bill for a patient.
     *
     * @param bill The bill object to be generated.
     * @return true if the bill is successfully created, false otherwise.
     */
    public boolean generateBill(Billing bill) {
        if (bill != null) {
            billingList.add(bill); // Adds the new bill to the list
            return true; // Successfully generated the bill
        }
        return false; // Failed to generate the bill
    }

    /**
     * Processes a bill payment.
     *
     * @param billID The ID of the bill to be paid.
     * @return true if the payment is successful, false otherwise.
     */
    public boolean processPayment(int billID) {
        Billing bill = getBillById(billID);
        if (bill != null && !bill.getPaymentStatus().equals("Paid")) {
            bill.confirmPayment(); // Confirms the payment for the bill
            return true; // Successfully processed the payment
        }
        return false; // Payment could not be processed (bill not found or already paid)
    }
}
