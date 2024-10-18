package src.view;

import src.controller.AdminController;
import src.controller.MedicineController;
import src.controller.PatientController;
import src.model.User;
import src.repository.MedicalRecordRepository;
import src.repository.MedicineRepository;
import src.repository.PatientRepository;
import src.repository.StaffRepository;

import java.util.Scanner;

public class MainMenuBoundary {
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayMenu(User user) {
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
    }
}
