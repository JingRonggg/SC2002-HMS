package src;

import src.repository.AppointmentRepository;
import src.controller.*;
import src.repository.MedicalRecordRepository;
import src.repository.MedicineRepository;
import src.repository.UserRepository;
import src.utils.MedicineCsvExporter;
import src.view.MainMenuBoundary;
import src.model.User;
import src.interfaces.IAdminRepository;
import src.interfaces.IPatientRepository;
import src.interfaces.IMedicalRecordRepository;
import src.repository.AdminRepository;
import src.repository.PatientRepository;
import src.repository.NurseRepository;
import java.util.Scanner;

public class Main {
    static UserRepository userRepository = new UserRepository();
    static MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
    static MedicineRepository medicineRepository = new MedicineRepository();
    static AppointmentRepository appointmentRepo = new AppointmentRepository();
    static NurseRepository nurseRepo = new NurseRepository();
    private static final AuthenticationController authController = new AuthenticationController(userRepository);
    private static final LoginController loginController = new LoginController(authController);

    public static void main(String[] args) {
        try {
            IAdminRepository adminRepo = new AdminRepository();
            IPatientRepository patientRepo = new PatientRepository();
            IMedicalRecordRepository medicalRecordRepo = new MedicalRecordRepository();

            AdminController adminController = new AdminController(adminRepo, appointmentRepo);
            PatientController patientController = new PatientController(patientRepo, medicalRecordRepo, adminRepo, appointmentRepo);
            MedicineController medicineController = new MedicineController(medicineRepository, medicalRecordRepo, appointmentRepo);
            DoctorController doctorController = new DoctorController(appointmentRepo, adminRepo, medicalRecordRepo, patientRepo);
            UserController userController = new UserController(userRepository);
            AppointmentController appointmentController = new AppointmentController(appointmentRepo);
            NurseController nurseController = new NurseController(nurseRepo, appointmentRepo);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                User loggedInUser = loginController.login(); // Attempt to log in

                if (loggedInUser != null) {
                    System.out.println("Login successful! Role: " + loggedInUser.getRole());
                    boolean continueSession = true;

                    // Session loop for the logged-in user
                    while (continueSession) {
                        continueSession = MainMenuBoundary.displayMenu(loggedInUser, adminController, patientController, medicineController, doctorController, nurseController);
                    }
                } else {
                    System.out.println("Login failed. Please try again.");
                    System.out.print("Would you like to try again? (yes/no): ");
                    String response = scanner.nextLine();
                    if (!response.equalsIgnoreCase("yes")) {
                        System.out.println("Exiting the application.");
                        saveAndExit();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred during initialization: " + e.getMessage());
        }
    }

    public static void saveAndExit() {
        appointmentRepo.saveAllToCsv();
        MedicineCsvExporter.exportAllMedicinesToCsv(medicineRepository);
        System.exit(0);
    }
}
