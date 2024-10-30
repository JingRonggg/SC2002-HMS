package src.utils;

import src.model.Appointment;
import src.model.AppointmentWrapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class AppointmentCsvExporter {
    protected static final String CSV_FILE_PATH = "./data/Appointment_List.csv";

    public static void exportAppointmentsToCsv(AppointmentWrapper appointment) {
        try{
            // Check if the file exists and is empty, then write headers
            if (!Files.exists(Paths.get(CSV_FILE_PATH)) || Files.size(Paths.get(CSV_FILE_PATH)) == 0) {
                writeHeaders();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
                String line = formatAppointmentToCsv(appointment);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while exporting appointments to CSV: " + e.getMessage());
        }
    }

    private static void writeHeaders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
            String headers = String.join(",",
                    "AppointmentID",
                    "PatientID",
                    "DoctorID",
                    "DoctorName",
                    "AppointmentDate",
                    "AppointmentStartTime",
                    "AppointmentEndTime",
                    "Status");
            writer.write(headers);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while writing headers to CSV: " + e.getMessage());
        }
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
                appointment.getStatus().toString()
        );
    }
}
