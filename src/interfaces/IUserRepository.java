package src.interfaces;

import src.model.User;

import java.util.Collection;

/**
 * Repository interface for managing user-related operations in the system.
 * Provides CRUD operations for user management.
 */
public interface IUserRepository {

    /**
     * Adds a new user to the system.
     *
     * @param user The user object to be added
     */
    void addUser(User user);

    /**
     * Retrieves a user by their hospital ID.
     *
     * @param id The hospital ID of the user
     * @return User object containing the user's information
     */
    User getUserByHospitalID(String id);

    /**
     * Retrieves all users registered in the system.
     *
     * @return Collection of all User objects
     */
    Collection<User> getAllUsers();

    /**
     * Updates an existing user's information.
     *
     * @param user The user object containing updated information
     */
    void updateUser(User user);

    /**
     * Deletes a user from the system.
     *
     * @param id The hospital ID of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    boolean deleteUser(String id);

    /**
     * Checks if a user exists in the system.
     *
     * @param hospitalID The hospital ID to check
     * @return true if the user exists, false otherwise
     */
    boolean userExists(String hospitalID);
}
