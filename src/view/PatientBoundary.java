package src.view;

import src.controller.PatientController;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PatientBoundary {
    private final PatientController patientController;
    private final String hospitalID;

    public PatientBoundary(String hospitalID) {
        patientController = new PatientController();
        this.hospitalID = hospitalID;
    }

    public void displayPatientMenu(Scanner scanner) {
        while (true) {
            System.out.println("Patient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    patientController.getPatientInformation(hospitalID);
                    patientController.viewMedicalRecord(hospitalID);
                    break;
                case 2:
                    patientController.updatePatientInformation(hospitalID);
                    System.out.println("Insert update personal information function");
                    break;
                case 3:
                    System.out.println("Insert view available appointment function");
                    break;
                case 4:
                    System.out.println("Insert schedule an appointment function");
                    break;
                case 5:
                    System.out.println("Insert reschedule an appointment function");
                    break;
                case 6:
                    System.out.println("Insert cancel an appointment function");
                    break;
                case 7:
                    System.out.println("Insert view scheduled appointment function");
                    break;
                case 8:
                    System.out.println("insert view past appointment function");
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
