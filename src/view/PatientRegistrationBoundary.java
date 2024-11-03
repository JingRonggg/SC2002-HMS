package src.view;

import src.controller.NurseController;
import src.model.Patient;
import src.utils.InputValidator;
import src.utils.PatientIDGenerator;

import java.util.Scanner;

public class PatientRegistrationBoundary {
    private final NurseController nurseController;
    private final Scanner scanner;

    public PatientRegistrationBoundary(NurseController nurseController) {
        this.nurseController = nurseController;
        this.scanner = new Scanner(System.in);
    }

    public void registerNewPatient() {
        System.out.println("\n=== Register New Patient ===");

        try {
            // Generate patient ID and check if it exists
            String patientID;

            // Get patient details
            System.out.print("Enter patient's full name: ");
            String fullName = scanner.nextLine().trim();
            if (fullName.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter patient's date of birth (DD/MM/YYYY): ");
            String dateOfBirth = scanner.nextLine().trim();
            if (!InputValidator.isValidDateFormat(dateOfBirth)) {
                System.out.println("Invalid date format. Please use DD/MM/YYYY.");
                return;
            }

            System.out.print("Enter patient's gender (M/F): ");
            String gender = scanner.nextLine().trim().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Invalid gender. Please enter M or F.");
                return;
            }

            System.out.print("Enter patient's blood type (A+/A-/B+/B-/O+/O-/AB+/AB-): ");
            String bloodType = scanner.nextLine().trim().toUpperCase();
            if (!InputValidator.isValidBloodType(bloodType)) {
                System.out.println("Invalid blood type.");
                return;
            }

            System.out.print("Enter patient's email address: ");
            String email = scanner.nextLine().trim();
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email format.");
                return;
            }

            do {
                patientID = PatientIDGenerator.generatePatientID();
                if (nurseController.isExistingPatient(patientID)) {
                    // System.out.println("Generated ID already exists, generating new ID...");
                    continue;
                }
                break;
            } while (true);

            System.out.println("Generated Patient ID: " + patientID);

            // Create patient object
            Patient newPatient = new Patient(
                patientID,
                fullName,
                dateOfBirth,
                gender,
                bloodType,
                email
            );

            // Register patient using controller
            String result = nurseController.registerNewPatient(newPatient);
            System.out.println(result);

        } catch (Exception e) {
            System.out.println("Error registering patient: " + e.getMessage());
        }
    }
} 