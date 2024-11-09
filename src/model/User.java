package src.model;

import src.utils.PasswordHasher;

/**
 * Abstract base class representing a user in the hospital system.
 * Contains common attributes and functionality for all user types.
 */
public abstract class User {
    private final String hospitalID;
    private final String role;
    private String name;
    private String gender;
    private String password;
    private String hashedPassword;

    /**
     * Constructs a new User with the specified details.
     *
     * @param hospitalID The unique hospital ID assigned to the user
     * @param role The role of the user in the hospital system
     * @param name The full name of the user
     * @param gender The gender of the user
     */
    public User(String hospitalID, String role, String name, String gender) {
        this.hospitalID = hospitalID;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.password = "password"; // Default password
        this.hashedPassword = PasswordHasher.hashPassword(this.password);
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender The new gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the gender of the user.
     *
     * @return The user's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the hospital ID of the user.
     *
     * @return The user's hospital ID
     */
    public String getHospitalID() {
        return hospitalID;
    }

    /**
     * Gets the plain text password of the user.
     *
     * @return The user's password in plain text
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the hashed password of the user.
     *
     * @return The user's hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets a new password for the user and updates the hashed password.
     *
     * @param newPassword The new password to set
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
        this.hashedPassword = PasswordHasher.hashPassword(newPassword);
    }

    /**
     * Sets both the hashed password and plain text password.
     *
     * @param hashedPassword The hashed password to set
     * @param plainPassword The plain text password to set
     */
    public void setHashedPassword(String hashedPassword, String plainPassword) {
        this.hashedPassword = hashedPassword;
        this.password = plainPassword;
    }

    /**
     * Gets the role of the user.
     *
     * @return The user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Verifies if the provided password matches the user's password.
     *
     * @param password The password to verify
     * @return true if the password matches, false otherwise
     */
    public boolean verifyPassword(String password) {
        return PasswordHasher.verifyPassword(password, this.hashedPassword);
    }
}
