package src.view;

import src.controller.*;
import src.model.User;

import java.util.Scanner;

/**
 * Boundary class that handles the display of the main menu based on user roles
 */
public class MainMenuBoundary {
    /** Scanner object for reading user input */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the appropriate menu based on the user's role
     * 
     * @param user The user object containing role and hospital ID information
     * @param adminController Controller for administrator operations
     * @param patientController Controller for patient operations
     * @param medicineController Controller for medicine operations
     * @param doctorController Controller for doctor operations
     * @param nurseController Controller for nurse operations
     * @return boolean indicating whether to continue displaying the menu
     */
    public static boolean displayMenu(User user, AdminController adminController, PatientController patientController, MedicineController medicineController, DoctorController doctorController, NurseController nurseController) {
        String role = user.getRole();
        String hospitalID = user.getHospitalID();

        switch (role.toUpperCase()) {
            case "ADMINISTRATOR":
                AdministratorBoundary administratorBoundary = new AdministratorBoundary(adminController, medicineController);
                administratorBoundary.displayAdministratorMenu(scanner);
                break;
            case "PATIENT":
                PatientBoundary patientBoundary = new PatientBoundary(patientController, hospitalID);
                patientBoundary.displayPatientMenu(scanner);
                break;
            case "DOCTOR":
                DoctorBoundary doctorBoundary = new DoctorBoundary(doctorController, hospitalID);
                doctorBoundary.displayDoctorMenu(scanner);
                break;
            case "PHARMACIST":
                PharmacistBoundary pharmacistBoundary = new PharmacistBoundary(medicineController);
                pharmacistBoundary.displayPharmacistMenu(scanner);
                break;
            case "NURSE":
                NurseBoundary nurseBoundary = new NurseBoundary(nurseController);
                nurseBoundary.displayNurseMenu(scanner);
                break;
            default:
                System.out.println("Invalid role");
        }
        return false;
    }
}
