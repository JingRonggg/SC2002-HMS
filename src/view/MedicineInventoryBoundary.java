package src.view;
import java.util.Scanner;
import src.controller.MedicineController;

public class MedicineInventoryBoundary {

    public static void displayMedicineInventory(MedicineController medicineController, Scanner scanner) {
        while (true) {
            System.out.println("1. Add Medicine");
            System.out.println("2. Remove Medicine");
            System.out.println("3. Update Stock level");
            System.out.println("4. Out");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left by nextInt
    
            switch (choice) {
                case 1:
                    medicineController.addNewMedicine();
                    break;
                case 2:
                    medicineController.removeMedicine();
                    break;
                case 3:
                    medicineController.updateMedicine();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
