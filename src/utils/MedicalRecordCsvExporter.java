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
    
        // Load existing records from the CSV file
        try {
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.equals(headers)) {
                            continue; // Skip headers
                        }
    
                        String[] fields = line.split(",");
                        if (fields.length > 0 && fields[0].equals(medicalRecordWrapper.getMedicalRecordID())) {
                            // If the record with the same MedicalRecordID is found, update it
                            for (String formattedLine : formatMedicalRecordToCsv(medicalRecordWrapper)) {
                                records.add(formattedLine);
                            }
                            recordUpdated = true;
                        } else {
                            // Keep the existing record if it doesn't match
                            records.add(line);
                        }
                    }
                }
            }
    
            // If the record wasn't updated, add the new record
            if (!recordUpdated) {
                records.addAll(formatMedicalRecordToCsv(medicalRecordWrapper));
            }
    
            // Write all records back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                writer.write(headers);
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
     * Formats a medical record into a single CSV line.
     * Concatenates multiple prescribed medications using "|" as a separator.
     *
     * @param medicalRecordWrapper The medical record wrapper to be formatted
     * @return A set of CSV formatted strings representing the medical record
     */
    private static Set<String> formatMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        Set<String> lines = new HashSet<>();
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
        String line = String.join(",",
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

        lines.add(line);
        return lines;
    }
}
