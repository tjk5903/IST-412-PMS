package model;

/**
 * Represents a hospital administrator responsible for managing hospital operations.
 */
public class hospitalAdmin {
    private int adminID;
    private String name;
    private String role;

    /**
     * Constructs a HospitalAdmin object.
     *
     * @param adminID The unique identifier of the administrator.
     * @param name The name of the administrator.
     * @param role The role of the administrator in the hospital.
     */
    public hospitalAdmin(int adminID, String name, String role) {
        this.adminID = adminID;
        this.name = name;
        this.role = role;
    }

    /**
     * Gets the administrator ID.
     *
     * @return The unique identifier of the administrator.
     */
    public int getAdminID() {
        return adminID;
    }

    /**
     * Gets the name of the administrator.
     *
     * @return The administrator's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the role of the administrator in the hospital.
     *
     * @return The administrator's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the administrator's name.
     *
     * @param name The new name of the administrator.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the administrator's role in the hospital.
     *
     * @param role The new role of the administrator.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Displays administrator details.
     */
    public void displayAdminDetails() {
        System.out.println("Admin ID: " + adminID);
        System.out.println("Name: " + name);
        System.out.println("Role: " + role);
    }
}
