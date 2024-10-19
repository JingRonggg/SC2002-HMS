package src;

import src.controller.UserController;
import src.repository.MedicalRecordRepository;
import src.repository.MedicineRepository;
import src.repository.UserRepository;
import src.view.MainMenuBoundary;
import src.controller.AuthenticationController;
import src.controller.LoginController;
import src.controller.AdminController;
import src.controller.PatientController;
import src.controller.MedicineController;
import src.model.User;
import src.repository.IAdminRepository;
import src.repository.IPatientRepository;
import src.repository.IMedicalRecordRepository;
import src.repository.AdminRepository;
import src.repository.PatientRepository;

import java.util.Scanner;

public class Main {
    static UserRepository userRepository = new UserRepository();
    static MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
    static MedicineRepository medicineRepository = new MedicineRepository();

    private static final AuthenticationController authController = new AuthenticationController(userRepository);
    private static final LoginController loginController = new LoginController(authController);

    public static void main(String[] args) {
        try {
            IAdminRepository adminRepo = new AdminRepository();
            IPatientRepository patientRepo = new PatientRepository();
            IMedicalRecordRepository medicalRecordRepo = new MedicalRecordRepository();

            AdminController adminController = new AdminController(adminRepo);
            PatientController patientController = new PatientController(patientRepo, medicalRecordRepo);
            MedicineController medicineController = new MedicineController(medicineRepository);
            UserController userController = new UserController(userRepository);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                User loggedInUser = loginController.login(); // Attempt to log in

                if (loggedInUser != null) {
                    System.out.println("Login successful! Role: " + loggedInUser.getRole());
                    boolean continueSession = true;

                    // Session loop for the logged-in user
                    while (continueSession) {
                        continueSession = MainMenuBoundary.displayMenu(loggedInUser, adminController, patientController, medicineController);
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
