package src.controller;

import src.model.User;
import src.interfaces.IUserRepository;
import src.repository.UserRepository;
import src.utils.PatientLoader;
import src.utils.StaffLoader;

import java.util.Collection;

/**
 * Controller class that manages user operations including loading, adding and deleting users.
 * This class serves as the main interface for user management in the hospital system.
 */
public class UserController {
    /** Repository interface for managing user data */
    private static IUserRepository userRepository;

    /**
     * Constructs a new UserController with the specified repository.
     * Initializes the controller and loads existing patients and staff.
     *
     * @param userRepo Repository for managing user data
     */
    public UserController(UserRepository userRepo) {
        this.userRepository = userRepo;
        loadPatients();
        loadStaff();
    }

    /**
     * Loads existing patients from a CSV file into the user repository.
     */
    private void loadPatients() {
        String patientFilePath = "./data/Patient_List.csv";
        PatientLoader patientLoader = new PatientLoader(patientFilePath);
        patientLoader.loadPatients();
    }

    /**
     * Loads existing staff from a CSV file into the user repository.
     */
    private void loadStaff() {
        String staffFilePath = "./data/Staff_List.csv";
        StaffLoader staffLoader = new StaffLoader(staffFilePath);
        staffLoader.loadStaff();
    }

    /**
     * Retrieves all users in the system.
     *
     * @return Collection containing all user objects
     */
    public static Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /**
     * Adds a new user to the system.
     *
     * @param user The user object to be added
     */
    public static void addUser(User user) {
        userRepository.addUser(user);
    }

    /**
     * Deletes a user from the system using their hospital ID.
     *
     * @param hospitalID The unique identifier for the user to be deleted
     */
    public static void deleteUser(String hospitalID) {
        userRepository.deleteUser(hospitalID);
    }
}
