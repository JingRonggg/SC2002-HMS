package src.utils;

import src.controller.AppointmentController;
import src.enums.AppointmentStatus;
import src.model.Appointment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class responsible for loading appointments from a CSV file into the system.
 */
public class AppointmentLoader {

    /**
     * Loads appointments from a CSV file into the system. This method performs two passes:
     * 1. First pass collects all existing appointment IDs to initialize the ID generator
     * 2. Second pass creates and adds the actual appointment objects to the system
     * 
     * The CSV file is expected to have the following format:
     * appointmentID,patientID,doctorID,doctorName,appointmentDate,appointmentStartTime,appointmentEndTime,status,consultationNotes
     * 
     * The method handles the following tasks:
     * - Reads and validates appointment data from CSV
     * - Initializes the AppointmentIDGenerator with existing IDs
     * - Creates Appointment objects from CSV data
     * - Adds appointments to the AppointmentController
     *
     * If any errors occur during file reading or data processing, appropriate error messages
     * are printed to the console.
     */
    public void loadAppointments() {
        Set<String> existingIDs = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AppointmentCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 8) {
                    existingIDs.add(fields[0]); // Collect existing IDs
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading appointments from CSV: " + e.getMessage());
        }

        // Initialize the generator with the collected IDs
        AppointmentIDGenerator.initializeWithExistingIDs(existingIDs);

        // Second pass: Load appointments
        try (BufferedReader reader = new BufferedReader(new FileReader(AppointmentCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 9) {
                    String appointmentID = fields[0];
                    String patientID = fields[1];
                    String doctorID = fields[2];
                    String doctorName = fields[3];
                    LocalDate appointmentDate = LocalDate.parse(fields[4]);
                    LocalTime appointmentStartTime = LocalTime.parse(fields[5]);
                    LocalTime appointmentEndTime = LocalTime.parse(fields[6]);
                    AppointmentStatus status = AppointmentStatus.valueOf(fields[7]);
                    String consultationNotes = fields[8];

                    Appointment appointment = new Appointment(doctorID, patientID, doctorName, appointmentDate, appointmentStartTime, appointmentEndTime, status, consultationNotes);
                    AppointmentController.addAppointment(appointmentID, appointment);
                    
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading appointments from CSV: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while processing the appointment data: " + e.getMessage());
        }
    }
}
