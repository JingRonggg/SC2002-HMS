package src.controller;

import src.model.User;
import src.interfaces.IUserRepository;
import src.repository.UserRepository;

import java.util.Scanner;

public class AuthenticationController {
    private static IUserRepository userRepository;

    public AuthenticationController(UserRepository userRepo) {
        try {
            this.userRepository = userRepo;
        } catch (Exception e) {
            System.out.println("Error initializing Authentication Controller: " + e.getMessage());
            System.out.println("Failed to initialize Authentication Controller");
        }
    }

    public User authenticate(String hospitalID, String password) {
        try {
            User user = userRepository.getUserByHospitalID(hospitalID);
            if (user != null && user.getPassword().equals(password)) {
                return user;  // Successful login
            }
            return null;  // Invalid credentials
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            return null;
        }
    }

    public boolean existenceCheck(String hospitalID) {
        try {
            return userRepository.userExists(hospitalID);
        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

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

    public boolean isPasswordCorrect(User user, String password) {
        try {
            return user.getPassword().equals(password);
        } catch (Exception e) {
            System.out.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
}
