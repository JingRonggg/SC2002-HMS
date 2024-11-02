package src.interfaces;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;
import java.util.List;

public interface IMedicalRecordRepository {
    // Crud operations on "Medical Records Table"

    // Create a new MedicalRecord
    void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications);

    // Read (retrieve) MedicalRecord by PatientID
    HashMap<String, MedicalRecord> readMedicalRecord(String patientID);

    // Get all MedicalRecords by PatientID & DoctorID (For Doctors)
    HashMap<String, MedicalRecord> getAllMedicalRecords(String doctorID);

    // Get MedicalRecord by MedicalRecordID
    MedicalRecord getMedicalRecordByID(String medicalRecordID);

    // Update an existing MedicalRecord
    boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications);

    // Delete a MedicalRecord by PatientID
    boolean deleteMedicalRecord(String medicalRecordID);
    boolean booleanReadUndispensedMedicalRecord(String patientID, String doctorID);
    HashMap<String, MedicalRecord> getAllUndispensedMedicalRecord();
    HashMap<String, MedicalRecord> readUndispensedMedicalRecord(String patientID, String doctorID);
}
