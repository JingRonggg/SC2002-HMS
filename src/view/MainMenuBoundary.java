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
                MedicineRepository medicineRepository = new MedicineRepository();
                MedicineController medicineController = new MedicineController(medicineRepository);
                StaffRepository staffRepository = new StaffRepository();
                AdminController adminController = new AdminController(staffRepository);
                AdministratorBoundary administratorBoundary = new AdministratorBoundary(adminController, medicineController);
                administratorBoundary.displayAdministratorMenu(scanner);
                break;
            case "PATIENT":
                PatientRepository patientRepository = new PatientRepository();
                MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
                PatientController patientController = new PatientController(patientRepository, medicalRecordRepository);
                PatientBoundary patientBoundary = new PatientBoundary(patientController, hospitalID);
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
