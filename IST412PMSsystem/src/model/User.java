package model;

// Abstract class for User
public abstract class User {
    protected int userID;
    protected String name;
    protected String contact;
    protected String role;  // Could be "Doctor", "Patient", or "Admin"

    public abstract String getUserInfo();
    public abstract String getRole();

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
