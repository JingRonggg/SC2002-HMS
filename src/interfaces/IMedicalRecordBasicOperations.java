package src.interfaces;

import src.model.MedicalRecord;

import java.util.HashMap;

public interface IMedicalRecordBasicOperations {
    void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord);
    // Read (retrieve) MedicalRecord by PatientID
    HashMap<String, MedicalRecord> readMedicalRecord(String patientID);
    // Get MedicalRecord by MedicalRecordID
    MedicalRecord getMedicalRecordByID(String medicalRecordID);
    // Delete a MedicalRecord by PatientID
    boolean deleteMedicalRecord(String medicalRecordID);
}
