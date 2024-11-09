package src.utils;

import src.model.Appointment;
import src.model.AppointmentWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for exporting appointment data to CSV format.
 * Handles both creating new CSV files and updating existing appointment records.
 */
public class AppointmentCsvExporter {
    /** The file path where the CSV file will be stored */
    protected static final String CSV_FILE_PATH = "./data/Appointment_List.csv";

    /**
     * Exports an appointment to a CSV file. If the appointment already exists (based on AppointmentID),
     * it will update the existing record. If the file doesn't exist, it will create a new one with headers.
     *
     * @param appointmentWrapper The appointment wrapper containing the appointment data to export
     */
    public static void exportAppointmentsToCsv(AppointmentWrapper appointmentWrapper) {
        List<String> lines = new ArrayList<>();
        boolean appointmentExists = false;

        try {
            // Read existing appointments
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Split line into fields
                        String[] fields = line.split(",");
                        // Check if this is the header line
                        if (fields[0].equals("AppointmentID")) {
                            lines.add(line); // Add headers
                            continue;
                        }
                        // Check for existing AppointmentID
                        if (fields[0].equals(appointmentWrapper.getAppointmentID())) {
                            // Update the existing appointment line
                            line = formatAppointmentToCsv(appointmentWrapper);
                            appointmentExists = true;
                        }
                        lines.add(line);
                    }
                }
            } else {
                writeHeaders(lines);
            }

            // If appointment doesn't exist, add it to the lines
            if (!appointmentExists) {
                lines.add(formatAppointmentToCsv(appointmentWrapper));
            }

            // Write all lines back to the CSV
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                for (String record : lines) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while exporting appointments to CSV: " + e.getMessage());
        }
    }

    /**
     * Writes the CSV header line to the provided list.
     *
     * @param lines The list to which the header line will be added
     */
    private static void writeHeaders(List<String> lines) {
        String headers = String.join(",",
                "AppointmentID",
                "PatientID",
                "DoctorID",
                "DoctorName",
                "AppointmentDate",
                "AppointmentStartTime",
                "AppointmentEndTime",
                "Status",
                "Consultation Notes");
        lines.add(headers);
    }

    /**
     * Formats an appointment into a CSV line string.
     *
     * @param appointmentWrapper The appointment wrapper containing the appointment to format
     * @return A comma-separated string containing all appointment fields
     */
    private static String formatAppointmentToCsv(AppointmentWrapper appointmentWrapper) {
        Appointment appointment = appointmentWrapper.getAppointment();
        return String.join(",",
                appointmentWrapper.getAppointmentID(),
                appointment.getPatientID(),
                appointment.getDoctorID(),
                appointment.getDoctorName(),
                appointment.getAppointmentDate().toString(),
                appointment.getAppointmentStartTime().toString(),
                appointment.getAppointmentEndTime().toString(),
                appointment.getStatus().toString(),
                appointment.getConsultationNotes()
        );
    }
}
