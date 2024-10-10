package src.view;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PharmacistBoundary {
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
                    break;
                case 3:
                    System.out.println("Insert View Medication Inventory function");
                    break;
                case 4:
                    System.out.println("Insert Submit Replenishment Request function");
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
