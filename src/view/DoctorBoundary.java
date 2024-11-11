package src.view;

import src.controller.DoctorController;
import src.model.Appointment;
import src.model.MedicalRecord;
import src.model.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Boundary class that handles the user interface for doctor-related operations
 */
public class DoctorBoundary {
    /** The controller that handles doctor business logic */
    private final DoctorController doctorController;
    
    /** The ID of the doctor currently logged in */
    private final String doctorID;

    /**
     * Constructs a new DoctorBoundary
     * @param doctorController The controller that handles doctor business logic
     * @param doctorID The ID of the doctor currently logged in
     */
    public DoctorBoundary(DoctorController doctorController, String doctorID) {
        this.doctorController = doctorController;
        this.doctorID = doctorID;
    }

    /**
     * Displays the main menu for doctor operations and handles user input
     * Menu options include:
     * 1. View Patient Medical Records
     * 2. Update Patient Medical Records 
     * 3. View Personal Schedule
     * 4. Set Availability for Appointments
     * 5. Accept or Decline Appointment Requests
     * 6. View Upcoming Appointments
     * 7. Record Appointment Outcome
     * 8. Logout
     *
     * @param scanner Scanner object to read user input
     */
    public void displayDoctorMenu(Scanner scanner) {
        while (true) {
            try{
                String patientID = null;
                String date = null;
                MedicalRecord medicalRecord = null;
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
                        try {
                            Collection<Patient> patients = doctorController.viewAllPatients();
                            PatientPrinter.patientPrinter(patients);
                            System.out.println("Which patient's medical record do you want to view? Enter their hospital ID");
                            patientID = scanner.nextLine();
                            Map<String, MedicalRecord> records = doctorController.viewMedicalRecordsUnderDoctor(doctorID, patientID);
                            if (records.isEmpty()) {
                                System.out.println("No medical records found for this patient.");
                            } else {
                                MedicalRecordPrinter.printMedicalRecordDetails(records);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                    //TODO display all appointment
                        try {
                            System.out.println("Which appointment do you want to update the medical record? (Insert appointmentID)");
                            String appointmentID = scanner.nextLine();
                            NewMedicalRecord.newMedicalRecord(doctorController, doctorID, appointmentID, null);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error updating Medical Record: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Enter the date you want to check your schedule (yyyy-MM-dd): ");
                        date = scanner.nextLine();
                        try {
                            LocalDate localDate = LocalDate.parse(date, formatter);
                            ArrayList<Appointment> availableSlots = doctorController.viewPersonalSchedule(doctorID, localDate);
                            AppointmentPrinter.printAvailableSlots(availableSlots, doctorID, localDate);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format");
                        }
                        break;
                        case 4:
                            try {
                                System.out.println("Enter a date (yyyy-MM-dd): ");
                                date = scanner.nextLine();
                                System.out.println("Enter start time (HH:mm): ");
                                String startTime = scanner.nextLine();
                                System.out.println("Enter end time (HH:mm): ");
                                String endTime = scanner.nextLine();
                                
                                LocalDate localDate = LocalDate.parse(date, formatter);
                                LocalTime startTimeParse = LocalTime.parse(startTime);
                                LocalTime endTimeParse = LocalTime.parse(endTime);
                                
                                if (startTimeParse.isAfter(endTimeParse)) {
                                    throw new IllegalArgumentException("Start time cannot be after end time");
                                }

                                doctorController.setAvailabilityForAppointments(doctorID, localDate, startTimeParse, endTimeParse);
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date or time format");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                    case 5:
                        if (doctorController.viewPendingAppointments(doctorID) != null) {
                            System.out.println("Pending Appointments for Doctor ID: " + doctorID);
                            AppointmentPrinter.printAppointmentDetails(doctorController.viewPendingAppointments(doctorID));
                            System.out.println("Which Appointment do you want to accept/decline?");
                            String appointmentID = scanner.nextLine();
                            System.out.println("Press 1 to accept, Press 2 to decline");
                            String outcome = scanner.nextLine();
                            if (outcome.equals("1")) {
                                doctorController.appointmentRequestOutcome(appointmentID, "Confirmed");
                                System.out.println("Appointment accepted!");
                                Appointment appointment = doctorController.findAppointment(appointmentID);
                                HashMap<String, Appointment> appointmentMap = new HashMap<>();
                                appointmentMap.put(appointmentID, appointment);
                                AppointmentPrinter.printAppointmentDetails(appointmentMap);
                            } else if (outcome.equals("2")) {
                                doctorController.appointmentRequestOutcome(appointmentID, "Cancelled");
                                System.out.println("Appointment declined!");
                                Appointment appointment = doctorController.findAppointment(appointmentID);
                                HashMap<String, Appointment> appointmentMap = new HashMap<>();
                                appointmentMap.put(appointmentID, appointment);
                                AppointmentPrinter.printAppointmentDetails(appointmentMap);
                            } else {
                                System.out.println("Invalid outcome");
                            }
                        }
                        break;
                    case 6:
                        System.out.println("Confirmed Appointments for Doctor ID: " + doctorID);
                        AppointmentPrinter.printAppointmentDetails(doctorController.viewUpComingAppointments(doctorID));
                        break;
                    case 7:
                        try {
                            System.out.println("Which appointment do you want to record the outcome of?");
                            String appointmentID = scanner.nextLine();
                            System.out.println("Enter consultation notes: ");
                            String consultationNotes = scanner.nextLine();
                            NewMedicalRecord.newMedicalRecord(doctorController, doctorID, appointmentID,consultationNotes);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error recording appointment outcome: " + e.getMessage());
                        }
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for your choice.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
