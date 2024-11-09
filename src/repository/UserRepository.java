package src.repository;

import src.interfaces.IUserRepository;
import src.model.User;

import java.util.Collection;
import java.util.HashMap;

/**
 * Repository class for managing User data and operations.
 * Implements IUserRepository interface to provide CRUD operations for User entities.
 */
public class UserRepository implements IUserRepository {
    /** HashMap to store users with hospitalID as key and User object as value */
    static HashMap<String, User> users = new HashMap<>(); // Changed to static

    /**
     * Default constructor for UserRepository.
     */
    public UserRepository() {}

    /**
     * Adds a new user to the repository.
     *
     * @param user The User object to be added
     */
    @Override
    public void addUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    /**
     * Retrieves a user by their hospital ID.
     *
     * @param hospitalID The unique hospital identifier for the user
     * @return User object if found, null otherwise
     */
    @Override
    public User getUserByHospitalID(String hospitalID) {
        return users.get(hospitalID);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return Collection of all User objects in the repository
     */
    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Updates an existing user's information in the repository.
     *
     * @param user The User object containing updated information
     */
    @Override
    public void updateUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    /**
     * Deletes a user from the repository.
     *
     * @param hospitalID The unique hospital identifier for the user to be deleted
     * @return true if user was successfully deleted, false if user doesn't exist
     */
    @Override
    public boolean deleteUser(String hospitalID) {
        if (users.containsKey(hospitalID)) {
            users.remove(hospitalID);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a user exists in the repository.
     *
     * @param hospitalID The unique hospital identifier to check
     * @return true if user exists, false otherwise
     */
    @Override
    public boolean userExists(String hospitalID) {
        return users.containsKey(hospitalID);
    }

    /**
     * Temporary method for debugging purposes.
     * Prints all users and their corresponding classes.
     * TODO: Delete later, for checking purposes only
     */
    public void checkUserClasses() {
        System.out.println("Checking user classes in repository:");
        for (User user : users.values()) {
            System.out.println("User ID: " + user.getHospitalID() + ", Class: " + user.getClass());
        }
    }
}