package model;

/**
 * Represents a user in the hospital management system.
 * A user can have different roles such as doctor, patient, or administrator.
 */
public class User {
    private int userID;
    private String name;
    private String email;
    private String password;
    private String role;

    /**
     * Constructs a User object.
     *
     * @param userID The unique identifier for the user.
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param password The password for user authentication.
     * @param role The role of the user (e.g., doctor, patient, admin).
     */
    public User(int userID, String name, String email, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user ID.
     *
     * @return The unique identifier of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's role.
     *
     * @return The user's role (e.g., doctor, patient, admin).
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The new role of the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Authenticates the user by validating their credentials.
     *
     * @param enteredPassword The password entered by the user.
     * @return True if authentication is successful, false otherwise.
     */
    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    /**
     * Displays user details.
     */
    public void displayUserDetails() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }
}
