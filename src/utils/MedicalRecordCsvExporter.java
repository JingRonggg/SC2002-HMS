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
    /** The file path where the CSV file will be stored */
    protected static final String CSV_FILE_PATH = "./data/MedicalRecord_List.csv";

    /**
     * Exports a medical record to CSV format. If the file exists, it will update an existing record
     * or append a new one. If the file doesn't exist, it will create a new file with headers.
     *
     * @param medicalRecordWrapper The medical record wrapper containing the record to be exported
     */
    public static void exportMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        List<String> lines = new ArrayList<>();
        boolean recordExists = false;
    
        try {
            // Check if file exists and read existing records
            if (Files.exists(Paths.get(CSV_FILE_PATH))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Split line into fields
                        String[] fields = line.split(",");
                        // Add headers if present
                        if (fields[0].equals("MedicalRecordID")) {
                            lines.add(line); // Add headers
                            continue;
                        }
                        // Check if this RecordID matches the current record's ID
                        if (fields[0].equals(medicalRecordWrapper.getMedicalRecordID())) {
                            // Update the existing record line
                            for (String formattedLine : formatMedicalRecordToCsv(medicalRecordWrapper)) {
                                lines.add(formattedLine);
                            }
                            recordExists = true;
                            continue;
                        }
                        lines.add(line);
                    }
                }
            } else {
                // File does not exist, so add headers
                writeHeaders(lines);
            }
    
            // If record doesn't exist, add it to the lines
            if (!recordExists) {
                lines.addAll(formatMedicalRecordToCsv(medicalRecordWrapper));
            }
    
            // Write all lines back to the CSV
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, false))) {
                for (String record : lines) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while exporting medical records to CSV: " + e.getMessage());
        }
    }

    /**
     * Writes the CSV header row to the list of lines.
     *
     * @param lines The list to which the headers will be added
     */
    private static void writeHeaders(List<String> lines) {
        String headers = String.join(",",
                "MedicalRecordID",
                "DoctorID",
                "PatientID",
                "Condition",
                "Diagnosis",
                "Treatments",
                "TreatmentDate",
                "TreatmentDetails",
                "MedicineName",
                "Quantity",
                "Status",
                "AppointmentID");
        lines.add(headers);
    }

    /**
     * Formats a medical record into CSV lines.
     * Creates one line for each prescribed medication in the medical record.
     *
     * @param medicalRecordWrapper The medical record wrapper to be formatted
     * @return A list of CSV formatted strings representing the medical record
     */
    private static List<String> formatMedicalRecordToCsv(MedicalRecordWrapper medicalRecordWrapper) {
        List<String> lines = new ArrayList<>();
        MedicalRecord medicalRecord = medicalRecordWrapper.getMedicalRecord();
        PastDiagnosis past = medicalRecord.getPastDiagnosis();
        Treatments treatment = medicalRecord.getTreatments();
        
        // Loop through each PrescribeMedications in the list
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
