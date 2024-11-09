package src.view;

import src.controller.AdminController;

import java.util.Scanner;

/**
 * Boundary class that handles the staff management user interface
 */
public class ManageStaffBoundary {
    /**
     * Displays the staff management menu and handles user input
     * 
     * @param adminController The controller handling admin operations
     * @param scanner Scanner object for reading user input
     */
    public static void displayMenu(AdminController adminController, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Staff ---");
            System.out.println("1. View All Staff");
            System.out.println("2. Add Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. Remove Staff");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    adminController.viewAllStaff();
                    break;
                case 2:
                    adminController.addStaff();
                    break;
                case 3:
                    adminController.updateStaff();
                    break;
                case 4:
                    adminController.removeStaff();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
