package src.controller;

import src.model.User;

import java.util.Scanner;

public class LoginController {
    private final AuthenticationController authController;
    private final Scanner scanner;

    public LoginController(AuthenticationController authController) {
        this.authController = authController;
        this.scanner = new Scanner(System.in);
    }

    private boolean isFirstLogin(User user) {
        // Check if the user is using the default password
        return "password".equals(user.getPassword());
    }

    public User login() {
        System.out.println("-----------------------------------------");
        System.out.println("Welcome to the Hospital Management System");
        System.out.println("-----------------------------------------");
        System.out.println("Enter your hospital ID: ");
        String hospitalID = scanner.nextLine();

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
    }

    public void changePassword(User loggedInUser) {
        boolean isPasswordChanged = false;

        while (!isPasswordChanged) {
            authController.changePassword(loggedInUser, scanner);
            System.out.println("Please enter your new password to verify: ");
            String newPassword = scanner.nextLine();

            if (authController.isPasswordCorrect(loggedInUser, newPassword)) {
                System.out.println("Password change verified successfully!");
                isPasswordChanged = true; // Exit the loop
            } else {
                System.out.println("Password verification failed! Please try again.");
            }
        }
    }
}
