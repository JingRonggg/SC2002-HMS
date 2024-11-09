package src.view;

import src.controller.NurseController;
import src.utils.InputValidator;
import src.utils.PatientIDGenerator;

import java.util.Scanner;

/**
 * Boundary class that handles the nurse interface and operations
 */
public class NurseBoundary {
    private final NurseController nurseController;
    private final Scanner scanner;
    private final PatientRegistrationBoundary patientRegistrationBoundary;

    /**
     * Constructs a new NurseBoundary with the specified controller
     *
     * @param nurseController The controller handling nurse operations
     */
    public NurseBoundary(NurseController nurseController) {
        this.nurseController = nurseController;
        this.scanner = new Scanner(System.in);
        this.patientRegistrationBoundary = new PatientRegistrationBoundary(nurseController);
    }

    /**
     * Displays the nurse menu and handles user input for nurse operations
     * 
     * @param scanner Scanner object to read user input
     */
    public void displayNurseMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Nurse Operations ===");
            System.out.println("1. Register New Patient");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        patientRegistrationBoundary.registerNewPatient();
                        break;
                    case 0:
                        System.out.println("Exiting nurse menu...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

}