package model;

public class Doctor extends User implements Observer {
    private String specialty;

    public Doctor(int userID, String name, String specialization, String contact, String password, String login, String role) {
        super(userID, name, contact, password, login, role);
        this.specialty = specialization;
    }

    @Override
    public String getUserInfo() {
        return "Doctor: " + name + ", Specialty: " + specialty + ", Contact: " + contact;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    @Override
    public void update(String message) {
        System.out.println("Notification for Doctor " + name + ": " + message);
    }
}
