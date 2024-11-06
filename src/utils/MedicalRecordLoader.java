package src.utils;

import src.controller.MedicalRecordController;
import src.enums.PrescribeMedicationsStatus;
import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.Treatments;
import src.model.PrescribeMedications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedicalRecordLoader {

    public static void loadMedicalRecords() {
        Set<String> existingIDs = new HashSet<>();

        // First pass: Collect existing medical record IDs
        try (BufferedReader reader = new BufferedReader(new FileReader(MedicalRecordCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String medicalRecordID = fields[0];
                    existingIDs.add(medicalRecordID);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading medical record IDs from CSV: " + e.getMessage());
        }
        // System.out.println("hello");
        // Initialize the generator with the collected IDs
        MedicalRecordIDGenerator.initializeWithExistingIDs(existingIDs);

        // Second pass: Load medical records
        try (BufferedReader reader = new BufferedReader(new FileReader(MedicalRecordCsvExporter.CSV_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.println("fields");
                if (fields.length >= 11) { // Ensure the line has enough fields
                    String medicalRecordID = fields[0];
                    String doctorID = fields[1];
                    String patientID = fields[2];
                
                    // Parsing PastDiagnosis
                    String conditionName = fields[3];
                    LocalDate diagnosisDate = LocalDate.parse(fields[4]); // Parse the date
                    PastDiagnosis pastDiagnosis = new PastDiagnosis(conditionName, diagnosisDate);
                    
                    System.out.println("diagnosis");
                    // Parsing Treatments
                    String treatmentName = fields[5]; // Treatment name
                    LocalDate treatmentDate = LocalDate.parse(fields[6]); // Parse the treatment date
                    String treatmentDetails = fields[7]; // Treatment details
                    Treatments treatments = new Treatments(treatmentName, treatmentDate, treatmentDetails);
                    System.out.println("quack");
                    
                    // Parsing PrescribedMedications
                    List<PrescribeMedications> prescribedMedications = new ArrayList<>();
                    String medicineName = fields[8];
                    int quantity = Integer.parseInt(fields[9]); // Parse quantity
                    PrescribeMedicationsStatus medicineStatus = PrescribeMedicationsStatus.valueOf(fields[10].toUpperCase()); 
                    prescribedMedications.add(new PrescribeMedications(medicineName, quantity, medicineStatus));
                    System.out.println("quack quack");

                    // Create and add MedicalRecord to the controller
                    MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, doctorID, patientID, pastDiagnosis, treatments, prescribedMedications);
                    MedicalRecordController.addMedicalRecord(medicalRecordID, medicalRecord);
                    System.out.println("LAODED");
                    
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading medical records from CSV: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while processing the medical record data: " + e.getMessage());
        }
    }
}
