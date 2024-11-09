package src.interfaces;

import src.model.MedicalRecord;

import java.util.HashMap;

/**
 * Interface defining basic CRUD operations for medical records
 */
public interface IMedicalRecordBasicOperations {
    /**
     * Adds a new medical record to the system
     * @param medicalRecordID Unique identifier for the medical record
     * @param medicalRecord The medical record object to be added
     */
    void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord);

    /**
     * Retrieves all medical records associated with a patient
     * @param patientID The unique identifier of the patient
     * @return HashMap containing medical record IDs mapped to their corresponding medical records
     */
    HashMap<String, MedicalRecord> readMedicalRecord(String patientID);

    /**
     * Retrieves a specific medical record by its ID
     * @param medicalRecordID The unique identifier of the medical record
     * @return The requested medical record, or null if not found
     */
    MedicalRecord getMedicalRecordByID(String medicalRecordID);

    /**
     * Deletes a medical record from the system
     * @param medicalRecordID The unique identifier of the medical record to be deleted
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteMedicalRecord(String medicalRecordID);
}
