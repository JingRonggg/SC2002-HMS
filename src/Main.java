package src;

import src.boundary.MainMenuBoundary;
import src.controller.AuthenticationController;
import src.controller.LoginController;
import src.model.User;
import src.utils.SystemInitialiser;

public class Main {
    private static final AuthenticationController authController = new AuthenticationController();
    private static final LoginController loginController = new LoginController(authController);

    public static void main(String[] args) {
        // Initialize system data
        SystemInitialiser.initialiser(authController);
        User loggedInUser = loginController.login();

        if (loggedInUser != null) {
            System.out.println("Login successful! Role: " + loggedInUser.getRole());
            MainMenuBoundary.displayMenu(loggedInUser);
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}