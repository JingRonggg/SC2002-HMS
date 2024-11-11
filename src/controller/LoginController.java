package src.controller;

import src.Main;
import src.model.User;

import java.util.Scanner;

/**
 * Controller class that handles user login operations including authentication and password management.
 * This class serves as the main interface between users and the login system.
 */
public class LoginController {
    /** Controller for handling user authentication */
    private final AuthenticationController authController;
    /** Scanner for reading user input */
    private final Scanner scanner;

    /**
     * Constructs a new LoginController with the specified authentication controller.
     * Initializes the scanner for user input.
     *
     * @param authController The authentication controller for verifying credentials
     * @throws RuntimeException if initialization fails
     */
    public LoginController(AuthenticationController authController) {
        try {
            this.authController = authController;
            this.scanner = new Scanner(System.in);
        } catch (Exception e) {
            System.out.println("Error initializing Login Controller: " + e.getMessage());
            throw new RuntimeException("Failed to initialize Login Controller");
        }
    }

    /**
     * Checks if this is the user's first login by verifying if they are using the default password.
     *
     * @param user The user to check
     * @return true if using default password, false otherwise
     */
    private boolean isFirstLogin(User user) {
        try {
            return "password".equals(user.getPassword());
        } catch (Exception e) {
            System.out.println("Error checking first login status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handles the user login process including authentication and first-time login checks.
     * Prompts user for credentials and validates them.
     *
     * @return authenticated User object if login successful, null otherwise
     */
    public User login() {
        try {
            System.out.println("-----------------------------------------");
            System.out.println("Welcome to the Hospital Management System");
            System.out.println("-----------------------------------------");
            System.out.println("Enter your hospital ID: (enter 'exit' to quit)");
            String hospitalID = scanner.nextLine();

            if (hospitalID.equalsIgnoreCase("exit")) {
                try {
                    Main.saveAndExit();
                } catch (Exception e) {
                    System.out.println("Error during system exit: " + e.getMessage());
                    System.exit(1);
                }
            }

            if (!authController.existenceCheck(hospitalID)) {
                System.out.println("Invalid hospital ID");
                return null;
            }

            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            
            User loggedInUser = authController.authenticate(hospitalID, password);

            if (loggedInUser != null && isFirstLogin(loggedInUser)) {
                System.out.println("You are using the default password and must change it.");
                changePassword(loggedInUser);
            }

            return loggedInUser;

        } catch (Exception e) {
            System.out.println("Error during login process: " + e.getMessage());
            return null;
        }
    }

    /**
     * Handles the password change process for a user.
     * Prompts for new password and verifies the change.
     *
     * @param loggedInUser The user changing their password
     * @throws RuntimeException if password change fails critically
     */
    public void changePassword(User loggedInUser) {
        try {
            boolean isPasswordChanged = false;

            while (!isPasswordChanged) {
                try {
                    authController.changePassword(loggedInUser, scanner);
                    System.out.println("Please enter your new password to verify: ");
                    String newPassword = scanner.nextLine();

                    if (authController.isPasswordCorrect(loggedInUser, newPassword)) {
                        System.out.println("Password change verified successfully!");
                        isPasswordChanged = true;
                    } else {
                        System.out.println("Password verification failed! Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error during password change attempt: " + e.getMessage());
                    System.out.println("Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Critical error during password change: " + e.getMessage());
            throw new RuntimeException("Failed to change password", e);
        }
    }
}
