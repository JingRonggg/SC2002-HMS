package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.ArrayList;

public interface IMedicalRecordRepository {
    // Crud operations on "Medical Records Table"

    // Create a new MedicalRecord
    public void createMedicalRecord (MedicalRecord medicalRecord, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications);

    // Read (retrieve) MedicalRecord by PatientID
    public ArrayList<MedicalRecord> readMedicalRecord(String patientID);

    // Update an existing MedicalRecord
    public void updateMedicalRecord(String medicalRecordID, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications);

    // Delete a MedicalRecord by PatientID
    public boolean deleteMedicalRecord(String medicalRecordID);
}
