package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;
import src.utils.AppointmentIDGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicalRecordRepository implements IMedicalRecordRepository {
    // Storage of medical records (Key: MedicalRecordID)
    protected static HashMap<String, MedicalRecord> medicalRecordData = new HashMap<>();

    // Create (Add) a new MedicalRecord
    @Override
    public void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        // Generate a new MedicalRecordID
        String medicalRecordID = AppointmentIDGenerator.nextAppointmentID();

        // Create a new MedicalRecord instance
        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications);

        // Add the medical record to the repository
        medicalRecordData.put(medicalRecordID, medicalRecord);
    }

    // Read (Retrieve) MedicalRecord by PatientID (Key)
    @Override
    public HashMap<String, MedicalRecord> readMedicalRecord(String patientID) {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID)) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the Medical Record: " + e.getMessage());
        }
        return medicalRecords;
    }

    // Get all MedicalRecord by DoctorID (For Doctors)
    public HashMap<String, MedicalRecord> getAllMedicalRecords(String doctorID) {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getDoctorID().equals(doctorID)) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the Medical Records: " + e.getMessage());
        }
        return medicalRecords;
    }

    // Get MedicalRecord by MedicalRecordID
    public MedicalRecord getMedicalRecordByID(String medicalRecordID) {
        try {
            return medicalRecordData.get(medicalRecordID);
        } catch (Exception e) {
            System.out.println("An error occurred while getting the Medical Record: " + e.getMessage());
        }
        return null;
    }

    // Update MedicalRecord by MedicalRecordID
    @Override
    public boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        if (!medicalRecordData.containsKey(medicalRecordID)) {
            System.out.println("No such medical record exists.");
            return false;
        } else {
            MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
            boolean updated = false;

            // Update treatments if provided
            if (treatments != null) {
                medicalRecord.setTreatments(treatments);
                updated = true;
            }

            // Update past diagnosis if provided
            if (pastDiagnosis != null) {
                medicalRecord.setPastDiagnosis(pastDiagnosis);
                updated = true;
            }

            // Update prescribe medications if provided
            if (newPrescribeMedications != null) {
                // Compare and update only if the list is different
                if (!medicalRecord.getPrescribeMedications().equals(newPrescribeMedications)) {
                    medicalRecord.setPrescribeMedications(newPrescribeMedications);
                    updated = true;
                }
            }

            // Only update the repository if any changes were made
            if (updated) {
                medicalRecordData.put(medicalRecordID, medicalRecord);
            }
            return updated;
        }
    }


    // Delete MedicalRecord by MedicalRecordID
    @Override
    public boolean deleteMedicalRecord(String medicalRecordID) {
        return medicalRecordData.remove(medicalRecordID) != null;
    }

    //TODO Delete later, for checking purposes
    public void checkMedicalRecordClasses() {
        System.out.println("Checking Medical Records in repository:");
        for (MedicalRecord medicalRecord : medicalRecordData.values()) {
            System.out.println("Treatments: " + medicalRecord.getTreatments());
            System.out.println("Past Diagnosis: " + medicalRecord.getPastDiagnosis());
        }
    }
}
