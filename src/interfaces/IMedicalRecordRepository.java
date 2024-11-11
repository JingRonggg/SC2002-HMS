package src.interfaces;

/**
 * Interface for managing medical record repository operations.
 * Extends basic medical record operations, doctor operations, and medicine operations.
 */
public interface IMedicalRecordRepository extends IMedicalRecordBasicOperations, IMedicalRecordDoctorOperations,IMedicalRecordMedicineOperations{
    /**
     * Stores the medical records data into a CSV file.
     */
    void storeIntoCsv();
}
