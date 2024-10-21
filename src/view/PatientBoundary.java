package src.view;

import src.appointment.Appointment;
import src.controller.PatientController;

import java.time.LocalDate;
import java.time.LocalTime;
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            switch (choice) {
                case 1:
                    patientController.getPatientInformation(hospitalID);
                    patientController.viewMedicalRecord(hospitalID);
                    break;
                case 2:
                    patientController.updatePatientInformation(hospitalID);
                case 3:
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
                    System.out.println("Enter the doctor's ID that you want to schedule an appointment with: ");
                    String doctorID = scanner.nextLine();
                    System.out.print("Enter a date (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    System.out.println("Enter a time (HH:mm): ");
                    String time = scanner.nextLine();
                    try{
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        LocalTime startTime = LocalTime.parse(time);
                        patientController.scheduleAppointment(doctorID, hospitalID, localDate, startTime);
                    } catch (DateTimeParseException e){
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;
                case 5:
                    System.out.println("Enter the appointment ID that you want to reschedule: ");
                    String appointmentID = scanner.nextLine();
                    System.out.print("Enter a new date (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    System.out.println("Enter a new time (HH:mm): ");
                    time = scanner.nextLine();
                    try{
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        LocalTime startTime = LocalTime.parse(time);
                        patientController.rescheduleAppointment(appointmentID, localDate, startTime);
                    } catch (DateTimeParseException e){
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;
                case 6:
                    System.out.println("Enter the appointment ID that you want to cancel: ");
                    appointmentID = scanner.nextLine();
                    patientController.cancelAppointment(appointmentID);
                    break;
                case 7:
                    patientController.viewScheduledAppointments(hospitalID);
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
