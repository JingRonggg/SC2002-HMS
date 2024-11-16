package src.interfaces;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;
import java.util.List;

/**
 * Interface defining operations for doctors to manage medical records
 */
public interface IMedicalRecordDoctorOperations {
    /**
     * Creates a new medical record for a patient
     * @param doctorID The unique identifier of the doctor creating the record
     * @param patientID The unique identifier of the patient
     * @param pastDiagnosis The past diagnosis information
     * @param treatments The treatments prescribed
     * @param newPrescribeMedications List of medications prescribed
     * @param appointmentID foreignkey to appointment
     */
    void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications, String appointmentID);

    /**
     * Retrieves all medical records for a specific patient created by a specific doctor
     * @param doctorID The unique identifier of the doctor
     * @param patientID The unique identifier of the patient
     * @return HashMap containing medical records mapped by their IDs
     */
    HashMap<String, MedicalRecord> getMedicalRecordsByDoctorAndPatientID(String doctorID, String patientID);

    /**
     * Updates an existing medical record
     * @param medicalRecordID The unique identifier of the medical record to update
     * @param pastDiagnosis The updated past diagnosis information
     * @param treatments The updated treatments
     * @param newPrescribeMedications Updated list of prescribed medications
     * @return true if update was successful, false otherwise
     */
    boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications);

}
