package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;

public interface IMedicalRecordRepository {
    // Crud operations on "Medical Records Table"

    // Create a new MedicalRecord
    public void createMedicalRecord (MedicalRecord medicalRecord, PastDiagnosis pastDiagnosis, Treatments treatments);

    // Read (retrieve) MedicalRecord by PatientID
    public HashMap<String, MedicalRecord> readMedicalRecord(String patientID);

    // Get all MedicalRecords by PatientID & DoctorID (For Doctors)
    public HashMap<String, MedicalRecord> getAllMedicalRecords(String doctorID);

    // Get MedicalRecord by MedicalRecordID
    public MedicalRecord getMedicalRecordByID(String medicalRecordID);

    // Update an existing MedicalRecord
    public boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments);

    // Delete a MedicalRecord by PatientID
    public boolean deleteMedicalRecord(String medicalRecordID);
}
