package src.interfaces;

import java.util.HashMap;

import src.model.MedicalRecord;

/**
 * Interface for managing medical record repository operations.
 * Extends basic medical record operations, doctor operations, and medicine operations.
 */
public interface IMedicalRecordRepository extends IMedicalRecordBasicOperations, IMedicalRecordDoctorOperations,IMedicalRecordMedicineOperations{
    /**
     * Stores the medical records data into a CSV file.
     */
    HashMap<String, MedicalRecord> getMedicalRecordByAppointmentID(String appointmentID);
    void storeIntoCsv();
}
