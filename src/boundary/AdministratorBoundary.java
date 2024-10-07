package src.boundary;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class AdministratorBoundary {
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
                    System.out.println("Insert View and Manage Hospital Staff function");
                    break;
                case 2:
                    System.out.println("Insert View Appointments Details function");
                    break;
                case 3:
                    System.out.println("Insert View and Manage Medication Inventory function");
                    break;
                case 4:
                    System.out.println("Insert Approve Replenishment Request function");
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
