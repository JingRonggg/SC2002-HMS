package src.interfaces;

import src.model.MedicalRecord;

import java.util.HashMap;

public interface IMedicalRecordMedicineOperations {
    boolean booleanReadUndispensedMedicalRecord(String patientID, String doctorID);
    HashMap<String, MedicalRecord> getAllUndispensedMedicalRecord();
    HashMap<String, MedicalRecord> readUndispensedMedicalRecord(String patientID, String doctorID);
}
