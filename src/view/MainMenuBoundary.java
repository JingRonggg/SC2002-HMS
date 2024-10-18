package src.view;

import src.model.User;

import java.util.Scanner;

public class MainMenuBoundary {
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean displayMenu(User user) {
        String role = user.getRole();
        String hospitalID = user.getHospitalID();
        switch (role.toUpperCase()) {
            case "ADMINISTRATOR":
                AdministratorBoundary administratorBoundary = new AdministratorBoundary();
                administratorBoundary.displayAdministratorMenu(scanner);
                break;
            case "PATIENT":
                PatientBoundary patientBoundary = new PatientBoundary(hospitalID);
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
        return false;
    }
}
