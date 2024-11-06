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

public class AppointmentCsvExporter {
    protected static final String CSV_FILE_PATH = "./data/Appointment_List.csv";

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
