package src.interfaces;

import src.model.MedicalRecord;

import java.util.HashMap;

/**
 * Interface defining operations for managing undispensed medical records
 */
public interface IMedicalRecordMedicineOperations {
    /**
     * Checks if there are undispensed medical records for a specific patient and doctor
     * @param patientID The ID of the patient
     * @param doctorID The ID of the doctor
     * @return true if undispensed records exist, false otherwise
     */
    boolean booleanReadUndispensedMedicalRecord(String patientID, String doctorID);

    /**
     * Retrieves all undispensed medical records in the system
     * @return HashMap containing all undispensed medical records
     */
    HashMap<String, MedicalRecord> getAllUndispensedMedicalRecord();

    /**
     * Retrieves undispensed medical records for a specific patient and doctor
     * @param patientID The ID of the patient
     * @param doctorID The ID of the doctor
     * @return HashMap containing the undispensed medical records for the specified patient and doctor
     */
    HashMap<String, MedicalRecord> readUndispensedMedicalRecord(String patientID, String doctorID);
}
