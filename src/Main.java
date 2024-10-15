package src;

import src.model.*;
import src.repository.MedicalRecordRepository;
import src.repository.MedicineRepository;
import src.repository.UserRepository;
import src.view.MainMenuBoundary;
import src.controller.AuthenticationController;
import src.controller.LoginController;
import src.controller.MedicineController;
import src.utils.SystemInitialiser;
import src.model.User;

public class Main {
    static UserRepository userRepository = new UserRepository();
    static MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
    static MedicineRepository medicineRepo = new MedicineRepository();
    private static final AuthenticationController authController = new AuthenticationController(userRepository);
    private static final LoginController loginController = new LoginController(authController);
    private static final MedicineController medicineController = new MedicineController(medicineRepo);

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