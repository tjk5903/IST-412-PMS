package view;

public class AdminView {

    // Display result of insurance verification
    public void displayInsuranceVerificationResult(boolean isVerified, String patientID) {
        if (isVerified) {
            System.out.println("Insurance verified for patient with ID: " + patientID);
        } else {
            System.out.println("Insurance not verified for patient with ID: " + patientID);
        }
    }
}
