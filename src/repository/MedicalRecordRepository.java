package src.repository;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.HashMap;

public class MedicalRecordRepository implements IMedicalRecordRepository {
    // Storage of medical records (Key: PatientID)
    protected static HashMap<String, MedicalRecord> MedicalRecord = new HashMap<>();

    // Create (Add) a new MedicalRecord
    @Override
    public void createMedicalRecord (MedicalRecord medicalRecord, Treatments treatments, PastDiagnosis pastDiagnosis, PrescribeMedications prescribeMedications) {
        if (MedicalRecord.containsKey(medicalRecord.getPatientID())) {
            throw new IllegalArgumentException("Medical record for this patient already exists.");
        }
        medicalRecord.addTreatments(treatments);
        medicalRecord.addPastDiagnosis(pastDiagnosis);
        medicalRecord.addPrescribeMedications(prescribeMedications);
        MedicalRecord.put(medicalRecord.getPatientID(), medicalRecord);
    }

    // Read (Retrieve) MedicalRecord by PatientID(Key)
    @Override
    public MedicalRecord readMedicalRecord(String patientID){
        return MedicalRecord.get(patientID);
    }

    // Update MedicalRecord by PatientID
    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        if (!MedicalRecord.containsKey(medicalRecord.getPatientID())) {
            throw new IllegalArgumentException("Medical record for this patient does not exist.");
        }
        MedicalRecord.put(medicalRecord.getPatientID(), medicalRecord);
    }

    // Delete MedicalRecord by PatientID
    @Override
    public boolean deleteMedicalRecord(String patientID){
        if (MedicalRecord.containsKey(patientID)) {
            MedicalRecord.remove(patientID);
            return true;
        }
        return false;
    }


    //TODO Delete later, for checking purposes
    public void checkMedicalRecordClasses() {
        System.out.println("Checking Medical Records in repository:");
        for (MedicalRecord medicalRecord : MedicalRecord.values()) {
            System.out.println("Treatments" + medicalRecord.getTreatments());
            System.out.println("Past Diagnosis" + medicalRecord.getPastDiagnosis());
            System.out.println("Prescribed Medications" + medicalRecord.getPrescribeMedications());
        }
    }


}
