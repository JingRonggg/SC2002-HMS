package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;
import src.utils.AppointmentIDGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class MedicalRecordRepository implements IMedicalRecordRepository {
    // Storage of medical records (Key: PatientID)
    protected static HashMap<String, MedicalRecord> medicalRecordData = new HashMap<>();

    // Create (Add) a new MedicalRecord
    @Override
    public void createMedicalRecord (MedicalRecord medicalRecord, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications) {
        if (medicalRecordData.containsKey(medicalRecord.getPatientID())) {
            throw new IllegalArgumentException("Medical record for this patient already exists.");
        }
        medicalRecord.setTreatments(treatments);
        medicalRecord.setPastDiagnosis(pastDiagnosis);
        medicalRecord.setPrescribeMedications(prescribeMedications);
        String appointmentID = AppointmentIDGenerator.nextAppointmentID();
        medicalRecordData.put(appointmentID, medicalRecord);
    }

    // Read (Retrieve) MedicalRecord by PatientID(Key)
    @Override
    public ArrayList<MedicalRecord> readMedicalRecord(String patientID){
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();

        for (MedicalRecord patientMedicalRecord : medicalRecordData.values()) {
            if (patientMedicalRecord.getPatientID().equals(patientID)) {
                medicalRecords.add(patientMedicalRecord);
            }
        }

        return medicalRecords;
    }

    // Update MedicalRecord by PatientID
    @Override
    public void updateMedicalRecord(String medicalRecordID, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications) {
        if (!medicalRecordData.containsKey(medicalRecordID)){
            System.out.println("No such medical record exists.");
        } else {
            MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
            if (!medicalRecord.getTreatments().equals(treatments)) {
                medicalRecord.setTreatments(treatments);
            }
            if (!medicalRecord.getPastDiagnosis().equals(pastDiagnosis)) {
                medicalRecord.setPastDiagnosis(pastDiagnosis);
            }
            if (!medicalRecord.getPrescribeMedications().equals(prescribeMedications)) {
                medicalRecord.setPrescribeMedications(prescribeMedications);
            }
            medicalRecordData.put(medicalRecordID, medicalRecord);
        }
    }

    // Delete MedicalRecord by PatientID
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
            System.out.println("Prescribed Medications" + medicalRecord.getPrescribeMedications());
        }
    }


}
