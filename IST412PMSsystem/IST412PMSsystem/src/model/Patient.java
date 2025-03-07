package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient in the Patient Management System.
 * Contains personal information and medical history.
 */
public class Patient {

    private int patientID;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String address;  // Add address field
    private List<String> medicalHistory;
    private static List<Patient> patientList = new ArrayList<>();

    /**
     * Constructs a new Patient object and adds it to the patient list.
     *
     * @param patientID The unique identifier for the patient.
     * @param name The name of the patient.
     * @param age The age of the patient.
     * @param gender The gender of the patient.
     * @param contact The contact details of the patient.
     * @param address The address of the patient.
     * @param medicalHistory The medical history of the patient.
     */
    public Patient(int patientID, String name, int age, String gender, String contact, String address, List<String> medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.address = address;  // Initialize address
        this.medicalHistory = medicalHistory != null ? new ArrayList<>(medicalHistory) : new ArrayList<>();
        patientList.add(this);
    }

    // Getter and Setter for Address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for PatientID
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for Gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter and Setter for Contact
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Getter and Setter for Medical History
    public List<String> getMedicalHistory() {
        return new ArrayList<>(medicalHistory);
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = new ArrayList<>(medicalHistory);
    }

    // Add Medical Record
    public void addMedicalRecord(String condition) {
        if (condition != null && !condition.trim().isEmpty()) {
            this.medicalHistory.add(condition);
        }
    }

    /**
     * Displays patient details.
     */
    public void displayPatientDetails() {
        System.out.println("=== Patient Details ===");
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Contact: " + contact);
        System.out.println("Address: " + address);  // Display address
        System.out.println("Medical History: " + (medicalHistory.isEmpty() ? "None" : String.join(", ", medicalHistory)));
        System.out.println("========================");
    }

    // Retrieve Patient by ID
    public static Patient getPatientByID(int id) {
        for (Patient patient : patientList) {
            if (patient.getPatientID() == id) {
                return patient;
            }
        }
        return null;
    }

    // Remove Patient by ID
    public static boolean removePatient(int id) {
        return patientList.removeIf(patient -> patient.getPatientID() == id);
    }

    // List All Registered Patients
    public static void listAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            System.out.println("=== List of Registered Patients ===");
            for (Patient patient : patientList) {
                patient.displayPatientDetails();
            }
        }
    }
}
