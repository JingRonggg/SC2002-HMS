package src;

import src.controller.UserController;
import src.controller.LoginController;
import src.model.User;
import src.view.MainMenuBoundary;

import java.util.Scanner;

public class Main {
    private static final LoginController loginController = new LoginController();

    public static void main(String[] args) {
        try {
            UserController initialiserController = new UserController();
            Scanner scanner = new Scanner(System.in);

            // Loop until a successful login or exit
            while (true) {
                User loggedInUser = loginController.login(); // Attempt to log in

                if (loggedInUser != null) {
                    System.out.println("Login successful! Role: " + loggedInUser.getRole());
                    boolean continueSession = true;

                    // Session loop for the logged-in user
                    while (continueSession) {
                        continueSession = MainMenuBoundary.displayMenu(loggedInUser);
                    }
                } else {
                    System.out.println("Login failed. Please try again.");
                    System.out.print("Would you like to try again? (yes/no): ");
                    String response = scanner.nextLine();
                    if (!response.equalsIgnoreCase("yes")) {
                        System.out.println("Exiting the application.");
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred during initialization: " + e.getMessage());
        }
    }
}
