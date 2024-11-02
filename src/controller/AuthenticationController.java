package src.controller;

import src.model.User;
import src.interfaces.IUserRepository;
import src.repository.UserRepository;

import java.util.Scanner;

public class AuthenticationController {
    private static IUserRepository userRepository;

    public AuthenticationController(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    public User authenticate(String hospitalID, String password) {
        User user = userRepository.getUserByHospitalID(hospitalID);
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Successful login
        }
        return null;  // Invalid credentials
    }

    public boolean existenceCheck(String hospitalID) {
        return userRepository.userExists(hospitalID);
    }

    public void changePassword(User user, Scanner scanner) {
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
    }

    public boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }
}
