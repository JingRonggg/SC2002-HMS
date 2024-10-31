package src.utils;

import src.enums.AppointmentStatus;
import src.model.Appointment;
import src.controller.AppointmentController; // Assuming you have this package
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class AppointmentLoader {

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
                if (fields.length == 8) {
                    String appointmentID = fields[0];
                    String patientID = fields[1];
                    String doctorID = fields[2];
                    String doctorName = fields[3];
                    LocalDate appointmentDate = LocalDate.parse(fields[4]);
                    LocalTime appointmentStartTime = LocalTime.parse(fields[5]);
                    LocalTime appointmentEndTime = LocalTime.parse(fields[6]);
                    AppointmentStatus status = AppointmentStatus.valueOf(fields[7]);

                    Appointment appointment = new Appointment(doctorID, patientID, doctorName, appointmentDate, appointmentStartTime, appointmentEndTime, status);
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
