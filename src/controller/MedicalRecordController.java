package src.controller;

import src.interfaces.IMedicalRecordRepository;
import src.model.MedicalRecord;
import src.utils.MedicalRecordLoader;

/**
 * Controller class that manages medical record operations including loading and adding records.
 * This class serves as the main interface between the application and medical record data.
 */
public class MedicalRecordController {
    /** Repository interface for managing medical record data storage and retrieval */
    private static IMedicalRecordRepository medicalRecordRepository;

    /**
     * Constructs a new MedicalRecordController with the specified repository.
     * Initializes the controller and loads any existing medical records from storage.
     *
     * @param medicalRecordRepository The repository implementation for managing medical record data
     */
    public MedicalRecordController(IMedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        loadMedicalRecords();
    }

    /**
     * Loads existing medical records from persistent storage into the repository.
     * Creates a new MedicalRecordLoader instance to handle the loading process.
     * This method is called during controller initialization.
     */
    private void loadMedicalRecords() {
        MedicalRecordLoader.loadMedicalRecords();
        System.out.println("added");
    }

    /**
     * Adds a new medical record to the repository with a specific ID.
     *
     * @param medicalRecordID The unique identifier for the medical record
     * @param medicalRecord The medical record object containing record details
     */
    public static void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord) {
        // Directly using the repository to store the medical record
        try {
            medicalRecordRepository.addMedicalRecord(medicalRecordID, medicalRecord);
        } catch (Exception e) {
            System.out.println("An error occurred while adding the appointment: " + e.getMessage());
        }
    }
    

}
