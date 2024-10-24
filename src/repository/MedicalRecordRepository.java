package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;
import src.utils.AppointmentIDGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class MedicalRecordRepository implements IMedicalRecordRepository {
    // Storage of medical records (Key: MedicalRecordID)
    protected static HashMap<String, MedicalRecord> medicalRecordData = new HashMap<>();

    // Create (Add) a new MedicalRecord
    @Override
    public void createMedicalRecord (MedicalRecord medicalRecord, PastDiagnosis pastDiagnosis, Treatments treatments) {
        medicalRecordData.put(medicalRecord.getMedicalRecordID(), medicalRecord);
        if (medicalRecordData.containsKey(medicalRecord.getPatientID())) {
            throw new IllegalArgumentException("Medical record for this patient already exists.");
        }
        medicalRecord.setPastDiagnosis(pastDiagnosis);
        medicalRecord.setTreatments(treatments);
        String appointmentID = AppointmentIDGenerator.nextAppointmentID();
        medicalRecordData.put(appointmentID, medicalRecord);
    }

    // Read (Retrieve) MedicalRecord by PatientID(Key)
    @Override
    public HashMap<String, MedicalRecord> readMedicalRecord(String patientID){
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try{
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID)) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch(Exception e){
        System.out.println("An error occurred while getting the Medical Record" + e.getMessage());
        }
        return medicalRecords;
    }

    // Get all MedicalRecord by DoctorID (For Doctors)
    public HashMap<String, MedicalRecord> getAllMedicalRecords(String doctorID){
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try{
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getDoctorID().equals(doctorID)) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch(Exception e){
            System.out.println("An error occurred while getting the Medical Records" + e.getMessage());
        }
        return medicalRecords;
    }

    // Get MedicalRecord by MedicalRecordID
    public MedicalRecord getMedicalRecordByID(String medicalRecordID){
        try{
            for (String medicalRecordsID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getMedicalRecordID().equals(medicalRecordID)) {
                    return medicalRecordData.get(medicalRecordID);
                }
            }
        } catch(Exception e){
            System.out.println("An error occurred while getting the Medical Record" + e.getMessage());
        }
        return null;
    }

    // Update MedicalRecord by MedicalRecordID
    @Override
    public boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments) {
        if (!medicalRecordData.containsKey(medicalRecordID)){
            System.out.println("No such medical record exists.");
            return false;
        } else {
            MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
            if (!medicalRecord.getTreatments().equals(treatments)) {
                medicalRecord.setTreatments(treatments);
            }
            if (!medicalRecord.getPastDiagnosis().equals(pastDiagnosis)) {
                medicalRecord.setPastDiagnosis(pastDiagnosis);
            }
            medicalRecordData.put(medicalRecordID, medicalRecord);
            return true;
        }
    }

    // Delete MedicalRecord by MedicalRecordID
    @Override
    public boolean deleteMedicalRecord(String medicalRecordID) {
        if (medicalRecordData.containsKey(medicalRecordID)) {
            medicalRecordData.remove(medicalRecordID);
            return true;
        }
        return false;
    }


    //TODO Delete later, for checking purposes
    public void checkMedicalRecordClasses() {
        System.out.println("Checking Medical Records in repository:");
        for (MedicalRecord medicalRecord : medicalRecordData.values()) {
            System.out.println("Treatments" + medicalRecord.getTreatments());
            System.out.println("Past Diagnosis" + medicalRecord.getPastDiagnosis());
        }
    }
}
