package src.utils;

import src.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for exporting medical records to CSV format.
 */
public class MedicalRecordCsvExporter {
    /** The file path where the CSV file will be stored */
    protected static final String CSV_FILE_PATH = "./data/MedicalRecord_List.csv";

    /**
     * Exports a medical record to CSV format. If the file exists, it will update an existing record
     * or append a new one. If the file doesn't exist, it will create a new file with headers.
     *
     * @param medicalRecordWrapper The medical record wrapper containing the record to be exported
     */
    public static void exportMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        Set<String> records = new HashSet<>();
        String headers = getHeaders();
        boolean recordUpdated = false;

        try {
            // Check if the file exists and load existing records (excluding headers) into the set
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.equals(headers)) {
                            continue; // Skip headers
                        }
                        records.add(line); // Add each record line to the set
                    }
                }
            }

            // Update or add the medical record
            for (String formattedLine : formatMedicalRecordToCsv(medicalRecordWrapper)) {
                if (records.contains(formattedLine)) {
                    recordUpdated = true; // Indicate that a duplicate was found and already exists
                }
                records.add(formattedLine); // Add new or updated record to the set
            }

            // Write all records back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                writer.write(headers); // Write headers first
                writer.newLine();
                for (String record : records) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while exporting medical records to CSV: " + e.getMessage());
        }
    }

    /**
     * Returns the CSV header row.
     *
     * @return The header row as a string
     */
    private static String getHeaders() {
        return String.join(",",
                "MedicalRecordID",
                "DoctorID",
                "PatientID",
                "Diagnosis",
                "DiagnosisDate",
                "Treatment",
                "TreatmentDate",
                "TreatmentDetails",
                "MedicineName",
                "Quantity",
                "Status",
                "AppointmentID");
    }

    /**
     * Formats a medical record into CSV lines.
     * Creates one line for each prescribed medication in the medical record.
     *
     * @param medicalRecordWrapper The medical record wrapper to be formatted
     * @return A list of CSV formatted strings representing the medical record
     */
    private static Set<String> formatMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        Set<String> lines = new HashSet<>();
        MedicalRecord medicalRecord = medicalRecordWrapper.getMedicalRecord();
        PastDiagnosis past = medicalRecord.getPastDiagnosis();
        Treatments treatment = medicalRecord.getTreatments();

        // Loop through each prescribed medication in the list
        for (PrescribeMedications medication : medicalRecord.getPrescribeMedications()) {
            String line = String.join(",",
                    medicalRecordWrapper.getMedicalRecordID(),
                    medicalRecord.getDoctorID(),
                    medicalRecord.getPatientID(),
                    past.getConditionName(),
                    past.getDiagnosisDate().toString(),
                    treatment.getTreatmentName(),
                    treatment.getTreatmentDate().toString(),
                    treatment.getTreatmentDetails(),
                    medication.getMedicineName(),
                    String.valueOf(medication.getQuantity()),
                    medication.getStatus().toString(),
                    medicalRecord.getAppointmentID()
            );
            lines.add(line);
        }
        return lines;
    }
}
