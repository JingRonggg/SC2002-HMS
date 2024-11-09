package src.model;

/**
 * Represents an Administrator in the hospital system.
 * Extends the Staff class to inherit basic staff member functionality.
 */
public class Administrator extends Staff {
    /**
     * Constructs a new Administrator with the specified details.
     *
     * @param adminID The unique hospital identifier for the administrator
     * @param adminName The full name of the administrator
     * @param role The role/position of the administrator
     * @param adminGender The gender of the administrator
     * @param age The age of the administrator
     */
    public Administrator(String adminID, String adminName, String role, String adminGender, String age) {
        super(adminID, adminName, role, adminGender, age);
    }

}