package src;

import src.controller.AuthenticationController;
import src.controller.LoginController;
import src.controller.MedicineController;
import src.model.User;
import src.repository.UserRepository;
import src.utils.SystemInitialiser;
import src.view.MainMenuBoundary;

public class Main {
    private static final AuthenticationController authController = new AuthenticationController();
    private static final LoginController loginController = new LoginController();
    private static final MedicineController medicineController = new MedicineController();

    public static void main(String[] args) {
        // Initialize system data
        SystemInitialiser.initialiser(authController, medicineController);
        User loggedInUser = loginController.login();

        if (loggedInUser != null) {
            System.out.println("Login successful! Role: " + loggedInUser.getRole());
            MainMenuBoundary.displayMenu(loggedInUser);
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}