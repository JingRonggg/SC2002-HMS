package src.view;

import src.controller.PatientController;
import src.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Boundary class that handles all patient-related user interface interactions.
 * This class provides a menu-driven interface for patients to interact with the system.
 */
public class PatientBoundary {
    /** The controller handling patient-related business logic */
    private final PatientController patientController;
    
    /** The unique identifier for the hospital */
    private final String hospitalID;
    
    /**
     * Constructs a new PatientBoundary with the specified controller and hospital ID.
     *
     * @param patientController The controller handling patient-related operations
     * @param hospitalID The unique identifier for the hospital
     */
    public PatientBoundary(PatientController patientController, String hospitalID) {
        this.patientController = patientController;
        this.hospitalID = hospitalID;
    }

    /**
     * Displays and handles the main patient menu interface.
     * Provides options for:
     * <ul>
     *   <li>Viewing medical records</li>
     *   <li>Updating personal information</li>
     *   <li>Managing appointments (view, schedule, reschedule, cancel)</li>
     *   <li>Viewing appointment history</li>
     * </ul>
     *
     * @param scanner Scanner object for reading user input
     */
    public void displayPatientMenu(Scanner scanner) {
        while (true) {
            try{
                String date = null;
                String time = null;
                String appointmentID = null;
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
                        try {
                            PatientInfoPrinter.printPatientInfo(patientController.getPatientInformation(hospitalID));
                            MedicalRecordPrinter.printMedicalRecordDetails(patientController.viewPatientMedicalRecords(hospitalID));
                        } catch (Exception e) {
                            System.out.println("Error accessing medical records: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            patientController.updatePatientInformation(hospitalID);
                        } catch (Exception e) {
                            System.out.println("Error updating patient information: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.print("Enter a date (yyyy-MM-dd): ");
                        date = scanner.nextLine();
                        try {
                            LocalDate localDate = LocalDate.parse(date, formatter);
                            patientController.viewAvailableSlots(localDate);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        }
                        break;
                    case 4:
                        try {
                            System.out.println("Enter the doctor's ID that you want to schedule an appointment with: ");
                            String doctorID = scanner.nextLine();
                            System.out.print("Enter a date (yyyy-MM-dd): ");
                            date = scanner.nextLine();
                            System.out.println("Enter a time (HH:mm): ");
                            time = scanner.nextLine();
                            
                            LocalDate localDate = LocalDate.parse(date, formatter);
                            LocalTime startTime = LocalTime.parse(time);
                            patientController.scheduleAppointment(doctorID, hospitalID, localDate, startTime);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date or time format. Please use yyyy-MM-dd for date and HH:mm for time.");
                        } catch (Exception e) {
                            System.out.println("Error scheduling appointment: " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.println("Enter the appointment ID that you want to reschedule: ");
                            appointmentID = scanner.nextLine();
                            if (!patientController.appointmentExists(appointmentID)) {
                                System.out.println("Appointment does not exist.");
                                break;
                            }
                            if(patientController.getAppointment(appointmentID).getPatientID().equals(hospitalID)){
                                System.out.print("Enter a new date (yyyy-MM-dd): ");
                                date = scanner.nextLine();
                                System.out.println("Enter a new time (HH:mm): ");
                                time = scanner.nextLine();
                                
                                LocalDate localDate = LocalDate.parse(date, formatter);
                                LocalTime startTime = LocalTime.parse(time);
                                System.out.println(patientController.rescheduleAppointment(appointmentID, localDate, startTime));
                            } else {
                                System.out.println("This is not your appointment");
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date or time format. Please use yyyy-MM-dd for date and HH:mm for time.");
                        } catch (Exception e) {
                            System.out.println("Error rescheduling appointment: " + e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            System.out.println("Enter the appointment ID that you want to cancel: ");
                            appointmentID = scanner.nextLine();
                            if (patientController.getAppointment(appointmentID) != null && patientController.getAppointment(appointmentID).getPatientID() != null && patientController.getAppointment(appointmentID).getPatientID().equals(hospitalID)) {
                                System.out.println(patientController.cancelAppointment(appointmentID));
                            }
                            else {
                                System.out.println("This is not your appointment");
                            }
                        } catch (Exception e) {
                            System.out.println("Error canceling appointment: " + e.getMessage());
                        }
                        break;
                    case 7:
                        try {
                            HashMap<String, Appointment> scheduledAppointments =patientController.viewScheduledAppointments(hospitalID);
                            System.out.println("Scheduled appointments found. Here is the list");
                            AppointmentPrinter.printScheduledAppointments(scheduledAppointments);
                        } catch (Exception e) {
                            System.out.println("Error viewing scheduled appointments: " + e.getMessage());
                        }
                        break;
                    case 8:
                        try {
                            System.out.println("Here is the list of the completed appointments and their respective appointment outcome record");
                            HashMap<String, Appointment> completedAppointments = patientController.viewCompletedAppointments(hospitalID);
                            AppointmentPrinter.printAppointmentDetails(completedAppointments);
                        } catch (Exception e) {
                            System.out.println("Error viewing completed appointments: " + e.getMessage());
                        }
                        break;
                    case 9:
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
