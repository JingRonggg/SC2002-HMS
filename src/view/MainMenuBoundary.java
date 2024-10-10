package src.view;

import src.controller.AdminController;
import src.model.User;
import src.repository.StaffRepository;

import java.util.Scanner;

public class MainMenuBoundary {
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayMenu(User user) {
        String role = user.getRole();
        switch (role.toUpperCase()) {
            case "ADMINISTRATOR":
                StaffRepository staffRepository = new StaffRepository();
                AdminController adminController = new AdminController(staffRepository);
                AdministratorBoundary administratorBoundary = new AdministratorBoundary(adminController);
                administratorBoundary.displayAdministratorMenu(scanner);
                break;
            case "PATIENT":
                PatientBoundary patientBoundary = new PatientBoundary();
                patientBoundary.displayPatientMenu(scanner);
                break;
            case "DOCTOR":
                DoctorBoundary doctorBoundary = new DoctorBoundary();
                doctorBoundary.displayDoctorMenu(scanner);
                break;
            case "PHARMACIST":
                PharmacistBoundary pharmacistBoundary = new PharmacistBoundary();
                pharmacistBoundary.displayPharmacistMenu(scanner);
                break;
            default:
                System.out.println("Invalid role");
        }
    }
}
