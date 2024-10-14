package src;

import src.model.*;
import src.repository.MedicalRecordRepository;
import src.repository.UserRepository;
import src.view.MainMenuBoundary;
import src.controller.AuthenticationController;
import src.controller.LoginController;
import src.utils.SystemInitialiser;
import src.model.User;

public class Main {
    static UserRepository userRepository = new UserRepository();
    static MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
    private static final AuthenticationController authController = new AuthenticationController(userRepository);
    private static final LoginController loginController = new LoginController(authController);

    public static void main(String[] args) {
        // Initialize system data
        SystemInitialiser.initialiser(authController);
        User loggedInUser = loginController.login();
        
        //TODO remove this later, checking the subclasses added into user hashmap
        ((UserRepository) userRepository).checkUserClasses();

        if (loggedInUser != null) {
            System.out.println("Login successful! Role: " + loggedInUser.getRole());
            MainMenuBoundary.displayMenu(loggedInUser);
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}