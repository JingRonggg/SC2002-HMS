package src.repository;

import src.enums.PrescribeMedicationsStatus;
import src.interfaces.IMedicalRecordRepository;
import src.model.MedicalRecord;
import src.model.MedicalRecordWrapper;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;
import src.utils.AppointmentIDGenerator;
import src.utils.MedicalRecordCsvExporter;

import java.util.HashMap;
import java.util.List;

public class MedicalRecordRepository implements IMedicalRecordRepository {
    // Storage of medical records (Key: MedicalRecordID)
    protected static HashMap<String, MedicalRecord> medicalRecordData = new HashMap<>();

    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + e.getMessage());
    }

    // Create (Add) a new MedicalRecord
    @Override
    public void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        try {
            // Generate a new MedicalRecordID
            String medicalRecordID = AppointmentIDGenerator.nextAppointmentID();

            // Create a new MedicalRecord instance
            MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications);

            // Add the medical record to the repository
            medicalRecordData.put(medicalRecordID, medicalRecord);
        } catch (Exception e) {
            handleException("creating the Medical Record", e);
        }
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
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    @Override
    public HashMap<String, MedicalRecord> readUndispensedMedicalRecord(String patientID, String doctorID) {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID) && medicalRecord.getDoctorID().equals(doctorID)) {

                    boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.NOT_DISPENSED);

                    if(hasUndispensedMedications) {
                        medicalRecords.put(medicalRecordID, medicalRecord);
                    }
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    @Override
    public HashMap<String, MedicalRecord> getAllUndispensedMedicalRecord() {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.NOT_DISPENSED);

                if(hasUndispensedMedications) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    @Override
    public boolean booleanReadUndispensedMedicalRecord(String patientID, String doctorID) {
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID) && medicalRecord.getDoctorID().equals(doctorID)) {

                    boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.NOT_DISPENSED);

                    if(hasUndispensedMedications) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return false;
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
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    // Get MedicalRecord by MedicalRecordID
    public MedicalRecord getMedicalRecordByID(String medicalRecordID) {
        try {
            return medicalRecordData.get(medicalRecordID);
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return null;
    }

    // Update MedicalRecord by MedicalRecordID
    @Override
    public boolean updateMedicalRecord(String medicalRecordID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        try {
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
        } catch (Exception e) {
            handleException("updating the Medical Record", e);
            return false;
        }
    }

    // Delete MedicalRecord by MedicalRecordID
    @Override
    public boolean deleteMedicalRecord(String medicalRecordID) {
        try {
            return medicalRecordData.remove(medicalRecordID) != null;
        } catch (Exception e) {
            handleException("deleting the Medical Record", e);
            return false;
        }
    }

    //TODO Delete later, for checking purposes
    public void checkMedicalRecordClasses() {
        try {
            System.out.println("Checking Medical Records in repository:");
            for (MedicalRecord medicalRecord : medicalRecordData.values()) {
                System.out.println("Treatments: " + medicalRecord.getTreatments());
                System.out.println("Past Diagnosis: " + medicalRecord.getPastDiagnosis());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while checking the Medical Record classes: " + e.getMessage());
        }
    }

    @Override
    public void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord) {
        try {
            medicalRecordData.put(medicalRecordID, medicalRecord);
        } catch (Exception e) {
            handleException("adding the Medical Record", e);
        }
    }

    @Override
    public void storeIntoCsv() {
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                MedicalRecordWrapper medicalRecordWithID = new MedicalRecordWrapper(medicalRecordID,medicalRecord);
                MedicalRecordCsvExporter.exportMedicalRecordToCsv(medicalRecordWithID);
            }
        } catch (Exception e) {
            handleException("saving Medical Record to CSV", e);
        }
    }
}
