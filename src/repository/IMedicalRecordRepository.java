package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

public interface IMedicalRecordRepository {
    // Crud operations on "Medical Records Table"
    // Create a new MedicalRecord
    void createMedicalRecord(MedicalRecord medicalRecord, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications);

    // Read (retrieve) MedicalRecord by PatientID
    MedicalRecord readMedicalRecord(String patientID);

    // Update an existing MedicalRecord
    void updateMedicalRecord(MedicalRecord medicalRecord);

    // Delete a MedicalRecord by PatientID
    boolean deleteMedicalRecord(String patientID);
}
