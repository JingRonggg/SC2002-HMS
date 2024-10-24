package src.view;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

import src.controller.AdminController;
import src.controller.MedicineController;

public class PharmacistBoundary {

    private final MedicineController medicineController;


    public PharmacistBoundary(MedicineController medicineController) {
       this.medicineController = medicineController;
    }
    public void displayPharmacistMenu(Scanner scanner) {
        while (true) {
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Insert View Appointment Outcome Record function");

                    break;
                case 2:
                    System.out.println("Insert Update Prescription Status function");
                    System.out.println("Dispense for which medical record? Provide the medical record ID");
                    String medicalRecordID = scanner.nextLine();
                    medicineController.dispenseMedicine(medicalRecordID);
                    break;
                case 3:
                    System.out.println("Insert View Medication Inventory function");
                    medicineController.displayAllMedicines();
                    break;
                case 4:
                    System.out.println("Insert Submit Replenishment Request function");
                    medicineController.reqMedicine();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
