package src.view;

import src.controller.MedicineController;
import src.controller.AdminController;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class AdministratorBoundary {
    private final AdminController adminController;
    private final MedicineController medicineController;

    public AdministratorBoundary(AdminController adminController, MedicineController medicineController) {
        this.adminController = adminController;
        this.medicineController = medicineController;
    }

    public void displayAdministratorMenu(Scanner scanner) {
        while (true) {
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    ManageStaffBoundary.displayMenu(adminController, scanner);
                    break;
                case 2:
                    adminController.viewAllAppointments();
                    break;
                case 3:
                    medicineController.displayAllMedicines();
                    MedicineInventoryBoundary.displayMedicineInventory(medicineController, scanner);
                    break;
                case 4:
                    medicineController.replenishMedicine();
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
