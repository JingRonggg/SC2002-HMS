package src.controller;

import src.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class AuthenticationController {
    private HashMap<String, User> users;

    public AuthenticationController() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    public User authenticate(String hospitalID, String password) {
        User user = users.get(hospitalID);
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Successful login
        }
        return null;  // Invalid credentials
    }

    public void changePassword(User user, Scanner scanner) {
        System.out.println("Enter your new password: ");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        System.out.println("Password changed successfully!");
    }

    public boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}
