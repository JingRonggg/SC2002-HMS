package src.utils;

import src.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for exporting medical records to CSV format.
 */
public class MedicalRecordCsvExporter {

    protected static final String CSV_FILE_PATH = "./data/MedicalRecord_List.csv";

    /**
     * Exports a medical record to CSV format. If the file exists, it will update an existing record
     * or append a new one. If the file doesn't exist, it will create a new file with headers.
     *
     * @param medicalRecordWrapper The medical record wrapper containing the record to be exported
     */
    public static void exportMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        List<String> records = new ArrayList<>();
        boolean recordExists = false;

        // Load existing records from the CSV file
        try {
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        if (fields[0].equals("MedicalRecordID")) {
                            records.add(line); // Add headers to records list
                            continue; // Skip headers
                        }

                        if (fields[0].equals(medicalRecordWrapper.getMedicalRecordID())) {
                            // If the record with the same MedicalRecordID exists, mark it and update the record
                            line = formatMedicalRecordToCsv(medicalRecordWrapper); // Add the updated record
                            recordExists = true;
                        } 

                        records.add(line);
                    }
                }
            } else {
                getHeaders(records); // Add headers if the file doesn't exist
            }

            // If the record doesn't exist, add the new record
            if (!recordExists) {
                records.add(formatMedicalRecordToCsv(medicalRecordWrapper)); // Add new record if not present
            }

            // Write all records back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
                for (String record : records) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while exporting medical records to CSV: " + e.getMessage());
        }
    }

    /**
     * Returns the CSV header row.
     *
     * @param records The list to which the header row will be added
     */
    private static void getHeaders(List<String> records) {
        String headers = String.join(",",
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
        records.add(headers);
    }

    /**
     * Formats a medical record into a single CSV line.
     * Concatenates multiple prescribed medications using "|" as a separator.
     *
     * @param medicalRecordWrapper The medical record wrapper to be formatted
     * @return A CSV formatted string representing the medical record
     */
    private static String formatMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        MedicalRecord medicalRecord = medicalRecordWrapper.getMedicalRecord();
        PastDiagnosis past = medicalRecord.getPastDiagnosis();
        Treatments treatment = medicalRecord.getTreatments();

        // Prepare the concatenated strings for medication details
        StringBuilder medicineNames = new StringBuilder();
        StringBuilder quantities = new StringBuilder();
        StringBuilder statuses = new StringBuilder();

        // Loop through each prescribed medication and build the concatenated strings
        for (PrescribeMedications medication : medicalRecord.getPrescribeMedications()) {
            if (medicineNames.length() > 0) {
                medicineNames.append("|");
                quantities.append("|");
                statuses.append("|");
            }
            medicineNames.append(medication.getMedicineName());
            quantities.append(medication.getQuantity());
            statuses.append(medication.getStatus().toString());
        }

        // Create the CSV line with all medication details concatenated
        return String.join(",",
                medicalRecordWrapper.getMedicalRecordID(),
                medicalRecord.getDoctorID(),
                medicalRecord.getPatientID(),
                past.getConditionName(),
                past.getDiagnosisDate().toString(),
                treatment.getTreatmentName(),
                treatment.getTreatmentDate().toString(),
                treatment.getTreatmentDetails(),
                medicineNames.toString(),
                quantities.toString(),
                statuses.toString(),
                medicalRecord.getAppointmentID()
        );
    }
}
