package src.interfaces;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;
import java.util.List;

public interface IMedicalRecordDoctorOperations {
    // Create a new MedicalRecord
    void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications);
    // Get all MedicalRecords by PatientID & DoctorID (For Doctors)
    HashMap<String, MedicalRecord> getAllMedicalRecords(String doctorID);
    // Update an existing MedicalRecord
    boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications);

}
