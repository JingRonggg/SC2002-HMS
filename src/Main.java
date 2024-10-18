package src;

import src.controller.AuthenticationController;
import src.controller.InitialiserUserController;
import src.controller.LoginController;
import src.model.User;
import src.view.MainMenuBoundary;

public class Main {
    private static final LoginController loginController = new LoginController();

    public static void main(String[] args) {
        try {
            InitialiserUserController initialiserController = new InitialiserUserController();

            User loggedInUser = loginController.login();

            if (loggedInUser != null) {
                System.out.println("Login successful! Role: " + loggedInUser.getRole());
                MainMenuBoundary.displayMenu(loggedInUser);
            } else {
                System.out.println("Login failed. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred during initialization: " + e.getMessage());
        }
    }
}
