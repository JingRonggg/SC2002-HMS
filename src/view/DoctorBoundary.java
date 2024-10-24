package src.view;

import src.controller.DoctorController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class DoctorBoundary {
    private final DoctorController doctorController;
    private final String doctorID;

    public DoctorBoundary(DoctorController doctorController, String doctorID) {
        this.doctorController = doctorController;
        this.doctorID = doctorID;
    }

    public void displayDoctorMenu(Scanner scanner) {
        while (true) {
            String patientID = null;
            String date = null;
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            switch (choice) {
                case 1:
                    doctorController.viewPatientMedicalRecords(doctorID);
                    break;
                case 2:
                    System.out.println("Enter the ID of the Medical Record that you would like to update");
                    String medicalRecordID = scanner.nextLine();
                    doctorController.updatePatientMedicalRecords(medicalRecordID);
                    break;
                case 3:
                    System.out.println("Enter the date you want to check your schedule (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        doctorController.viewPersonalSchedule(doctorID, localDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format");
                    }
                    break;
                case 4:
                    System.out.println("Enter a date (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    System.out.println("Enter start time (HH:mm): ");
                    String startTime = scanner.nextLine();
                    System.out.println("Enter end time (HH:mm): ");
                    String endTime = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        LocalTime startTimeParse = LocalTime.parse(startTime);
                        LocalTime endTimeParse = LocalTime.parse(endTime);
                        doctorController.setAvailabilityForAppointments(doctorID, localDate, startTimeParse, endTimeParse);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format");
                    }
                    break;
                case 5:
                    if (doctorController.viewPendingAppointments(doctorID)) {
                        System.out.println("Which Appointment do you want to accept/decline?");
                        String appointmentID = scanner.nextLine();
                        System.out.println("Press 1 to accept, Press 2 to decline");
                        String outcome = scanner.nextLine();
                        if (outcome.equals("1")) {
                            doctorController.appointmentRequestOutcome(appointmentID, "Confirmed");
                        } else if (outcome.equals("2")) {
                            doctorController.appointmentRequestOutcome(appointmentID, "Declined");
                        } else {
                            System.out.println("Invalid outcome");
                        }
                    }
                    break;
                case 6:
                    doctorController.viewUpComingAppointments(doctorID);
                    break;
                case 7:
                    System.out.println("Insert Record Appointment Outcome function");
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
