package src.controller;

import src.model.User;
import src.repository.IUserRepository;
import src.repository.UserRepository;

import java.util.Scanner;

public class AuthenticationController {
    private static IUserRepository userRepository;

    public AuthenticationController() {
        userRepository = new UserRepository();
    }

    public User authenticate(String hospitalID, String password) {
        User user = userRepository.getUserByHospitalID(hospitalID);
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Successful login
        }
        return null;  // Invalid credentials
    }

    public void changePassword(User user, Scanner scanner) {
        System.out.println("Enter your new password: ");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        userRepository.updateUser(user);
        System.out.println("Password changed successfully!");
    }

    public boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }
}
