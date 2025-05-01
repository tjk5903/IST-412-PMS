package model;

public class Admin extends User {
    public Admin(String name, String contact) {
        this.name = name;
        this.contact = contact;
        this.role = "Admin";
    }

    public Admin(int id, String name, String contact, String password, String login, String role) {
        super(id, name, contact, password, login, role);
    }
    @Override
    public String getUserInfo() {
        return "Admin: " + name + ", Contact: " + contact;
    }

    @Override
    public String getRole() {
        return this.role;
    }
}
