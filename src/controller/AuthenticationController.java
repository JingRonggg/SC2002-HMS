package src.controller;

import src.model.User;
import src.interfaces.IUserRepository;
import src.repository.UserRepository;

import java.util.Scanner;

/**
 * Controller class that handles user authentication operations including login, password management and user verification.
 * This class serves as the main interface between the application and user authentication.
 */
public class AuthenticationController {
    /** Repository interface for managing user data storage and retrieval */
    private static IUserRepository userRepository;

    /**
     * Constructs a new AuthenticationController with the specified repository.
     * 
     * @param userRepo The repository implementation for managing user data
     */
    public AuthenticationController(UserRepository userRepo) {
        try {
            this.userRepository = userRepo;
        } catch (Exception e) {
            System.out.println("Error initializing Authentication Controller: " + e.getMessage());
            System.out.println("Failed to initialize Authentication Controller");
        }
    }

    /**
     * Authenticates a user based on hospital ID and password.
     * 
     * @param hospitalID The unique hospital identifier for the user
     * @param password The password to verify
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String hospitalID, String password) {
        try {
            User user = userRepository.getUserByHospitalID(hospitalID);
            if (user != null && user.verifyPassword(password)) {
                return user;  // Successful login
            }
            return null;  // Invalid credentials
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a user exists in the system based on hospital ID.
     * 
     * @param hospitalID The hospital ID to check
     * @return true if user exists, false otherwise
     */
    public boolean existenceCheck(String hospitalID) {
        try {
            return userRepository.userExists(hospitalID);
        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    /**
     * Allows a user to change their password through interactive console input.
     * Validates password requirements and updates the user's password in the repository.
     * 
     * @param user The user whose password is being changed
     * @param scanner Scanner object for reading user input
     */
    public void changePassword(User user, Scanner scanner) {
        try {
            String newPassword = "";
        
            do {
                System.out.println("Enter your new password: ");
                newPassword = scanner.nextLine();
                
                if (newPassword.trim().isEmpty()) {
                    System.out.println("Password cannot be empty. Please choose a valid password.");
                } else if (newPassword.contains(" ")) {
                    System.out.println("Password cannot contain spaces. Please try again.");
                } else if ("enter".equalsIgnoreCase(newPassword)) {
                    System.out.println("Password cannot be 'enter'. Please choose a different password.");
                } else {
                    user.setPassword(newPassword);
                    userRepository.updateUser(user);
                    System.out.println("Password changed successfully!");
                    break;
                }
                
            } while (true);
        } catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
    }

    /**
     * Verifies if a given password matches a user's stored password.
     * 
     * @param user The user whose password is being verified
     * @param password The password to verify
     * @return true if password matches, false otherwise
     */
    public boolean isPasswordCorrect(User user, String password) {
        try {
            return user.verifyPassword(password);
        } catch (Exception e) {
            System.out.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
}
