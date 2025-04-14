package model;

public class Patient extends User {
    private int age;
    private String gender;
    private String address;

    public Patient(int userID, String name, int age, String gender, String contact, String address, String password, String login, String role) {
        super(userID, name, contact, password, login, role);
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    @Override
    public String getUserInfo() {
        return "Patient: " + name + ", Age: " + age + ", Gender: " + gender + ", Address: " + address;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}