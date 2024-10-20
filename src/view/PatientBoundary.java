package src.view;

import src.appointment.Appointment;
import src.controller.PatientController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PatientBoundary {
    private final PatientController patientController;
    private final String hospitalID;
    

    public PatientBoundary(PatientController patientController, String hospitalID) {
        this.patientController = patientController;
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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.print("Enter a date (yyyy-MM-dd): ");
                    String date = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        patientController.viewAvailableSlots(localDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
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
                    System.out.println("Insert view past appointment function");
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
