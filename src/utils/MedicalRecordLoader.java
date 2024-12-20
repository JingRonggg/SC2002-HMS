package src.utils;

import src.controller.MedicalRecordController;
import src.model.PrescribeMedicationsStatus;
import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for loading medical records from a CSV file.
 */
public class MedicalRecordLoader {

    /**
     * Loads medical records from a CSV file and initializes them in the system.
     * This method performs two passes over the CSV file:
     * 1. First pass collects existing medical record IDs to initialize the ID generator
     * 2. Second pass creates and loads the actual medical record objects
     * 
     * The CSV file is expected to have the following format:
     * medicalRecordID,doctorID,patientID,conditionName,diagnosisDate,treatmentName,
     * treatmentDate,treatmentDetails,medicineName,quantity,medicineStatus
     * 
     * Each medical record entry must contain at least 11 fields to be processed successfully.
     * 
     * The method handles:
     * - Loading and parsing of CSV data
     * - Creation of PastDiagnosis objects
     * - Creation of Treatments objects
     * - Creation of PrescribeMedications objects
     * - Assembly of complete MedicalRecord objects
     * - Adding records to the MedicalRecordController
     * 
     * Any errors during file reading or data processing are caught and logged to System.out.
     */
    public static void loadMedicalRecords() {
        Set<String> existingMedicalRecordIDs= new HashSet<>();

        // First pass: Collect existing medical record IDs
        try (BufferedReader reader = new BufferedReader(new FileReader(MedicalRecordCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 12) {
                    existingMedicalRecordIDs.add(fields[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading medical record IDs from CSV: " + e.getMessage());
        }
        // System.out.println("hello");
        // Initialize the generator with the collected IDs
        MedicalRecordIDGenerator.initializeWithExistingIDs(existingMedicalRecordIDs);

        // Second pass: Load medical records
        try (BufferedReader reader = new BufferedReader(new FileReader(MedicalRecordCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 12) { // Ensure the line has enough fields
                    String medicalRecordID = fields[0];
                    String doctorID = fields[1];
                    String patientID = fields[2];
            
                    // Parsing PastDiagnosis
                    String conditionName = fields[3];
                    LocalDate diagnosisDate = LocalDate.parse(fields[4]); // Parse the date
                    PastDiagnosis pastDiagnosis = new PastDiagnosis(conditionName, diagnosisDate);
                    
                    // Parsing Treatments
                    String treatmentName = fields[5]; // Treatment name
                    LocalDate treatmentDate = LocalDate.parse(fields[6]); // Parse the treatment date
                    String treatmentDetails = fields[7]; // Treatment details
                    Treatments treatments = new Treatments(treatmentName, treatmentDate, treatmentDetails);
                    
                    // Parsing PrescribedMedications
                    // Parsing PrescribedMedications
                    List<PrescribeMedications> prescribedMedications = new ArrayList<>();
                    String[] medicineNames = fields[8].split("\\|"); // Split medicine names by "|"
                    String[] quantities = fields[9].split("\\|");    // Split quantities by "|"
                    String[] statuses = fields[10].split("\\|");     // Split statuses by "|"

                    // Iterate through all medications and create PrescribeMedications objects
                    for (int i = 0; i < medicineNames.length; i++) {
                        String medicineName = medicineNames[i].trim();
                        int quantity = Integer.parseInt(quantities[i].trim());
                        // Ensure the status string is properly formatted
                        String statusString = statuses[i].trim().toUpperCase();
                        
                        try {
                            PrescribeMedicationsStatus medicineStatus = PrescribeMedicationsStatus.valueOf(statusString);
                            prescribedMedications.add(new PrescribeMedications(medicineName, quantity, medicineStatus));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status found: " + statusString);
                            // Handle the invalid status case, perhaps by skipping or assigning a default status
                        }
                    }

                  
                    String appointmentID = fields[11];

                    // Create and add MedicalRecord to the controller
                    MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, doctorID, patientID, pastDiagnosis, treatments, prescribedMedications,appointmentID);
                    MedicalRecordController.addMedicalRecord(medicalRecordID, medicalRecord);

                    
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading medical records from CSV: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while processing the medical record data: " + e.getMessage());
        }
    }
}
