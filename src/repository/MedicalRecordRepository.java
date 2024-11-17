package src.repository;

import src.model.PrescribeMedicationsStatus;
import src.interfaces.IMedicalRecordRepository;
import src.model.*;
import src.utils.MedicalRecordCsvExporter;
import src.utils.MedicalRecordIDGenerator;

import java.util.HashMap;
import java.util.List;

/**
 * Repository class for managing medical records in the system.
 * Implements the IMedicalRecordRepository interface to provide CRUD operations and other medical record-related functionalities.
 */
public class MedicalRecordRepository implements IMedicalRecordRepository {
    /** HashMap to store medical records with medicalRecordID as key and MedicalRecord object as value */
    protected static HashMap<String, MedicalRecord> medicalRecordData = new HashMap<>();

    /**
     * Handles exceptions that occur during medical record operations
     * @param operation The operation during which the exception occurred
     * @param e The exception that was thrown
     */
    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + e.getMessage());
    }

    /**
     * Creates a new medical record in the system
     * @param doctorID The ID of the doctor creating the record
     * @param patientID The ID of the patient the record belongs to
     * @param pastDiagnosis The past diagnosis information
     * @param treatments The treatments information
     * @param newPrescribeMedications List of prescribed medications
     * @param appointmentID foreignkey to appointment
     */
    @Override
    public void createMedicalRecord(String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications, String appointmentID) {
        try {
            // Generate a new MedicalRecordID
            String medicalRecordID = MedicalRecordIDGenerator.nextMedicalRecordID();

            // Create a new MedicalRecord instance
            MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications, appointmentID);

            // Add the medical record to the repository
            medicalRecordData.put(medicalRecordID, medicalRecord);
        } catch (Exception e) {
            handleException("creating the Medical Record", e);
        }
    }

    public HashMap<String, MedicalRecord> getMedicalRecordByAppointmentID(String appointmentID) {
        HashMap<String, MedicalRecord> matchingRecords = new HashMap<>();
        try {
            for (MedicalRecord medicalRecord : medicalRecordData.values()) {
                if (medicalRecord.getAppointmentID().equals(appointmentID)) {
                    matchingRecords.put(medicalRecord.getMedicalRecordID(), medicalRecord); // Add to the HashMap
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record by Appointment ID", e);
        }
        return matchingRecords; // Return the HashMap of matching records
    }

    /**
     * Retrieves all medical records for a specific patient
     * @param patientID The ID of the patient
     * @return HashMap containing medical records for the patient
     */
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

    /**
     * Retrieves undispensed medical records for a specific patient and doctor
     * @param patientID The ID of the patient
     * @param doctorID The ID of the doctor
     * @return HashMap containing undispensed medical records
     */
    @Override
    public HashMap<String, MedicalRecord> readUndispensedMedicalRecord(String patientID, String doctorID) {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID) && medicalRecord.getDoctorID().equals(doctorID)) {

                    boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.PENDING);

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

    /**
     * Retrieves all undispensed medical records in the system
     * @return HashMap containing all undispensed medical records
     */
    @Override
    public HashMap<String, MedicalRecord> getAllUndispensedMedicalRecord() {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.PENDING);

                if(hasUndispensedMedications) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    /**
     * Checks if there are any undispensed medical records for a specific patient and doctor
     * @param patientID The ID of the patient
     * @param doctorID The ID of the doctor
     * @return boolean indicating if undispensed records exist
     */
    @Override
    public boolean booleanReadUndispensedMedicalRecord(String patientID, String doctorID) {
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getPatientID().equals(patientID) && medicalRecord.getDoctorID().equals(doctorID)) {

                    boolean hasUndispensedMedications = medicalRecord.getPrescribeMedications().stream().anyMatch(med -> med.getStatus() == PrescribeMedicationsStatus.PENDING);

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

    /**
     * Retrieves medical records for a specific doctor and patient combination
     * @param doctorID The ID of the doctor
     * @param patientID The ID of the patient
     * @return HashMap containing matching medical records
     */
    public HashMap<String, MedicalRecord> getMedicalRecordsByDoctorAndPatientID(String doctorID, String patientID) {
        HashMap<String, MedicalRecord> medicalRecords = new HashMap<>();
        try {
            for (String medicalRecordID : medicalRecordData.keySet()) {
                MedicalRecord medicalRecord = medicalRecordData.get(medicalRecordID);
                if (medicalRecord.getDoctorID().equals(doctorID) &&
                        medicalRecord.getPatientID().equals(patientID)) {
                    medicalRecords.put(medicalRecordID, medicalRecord);
                }
            }
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return medicalRecords;
    }

    /**
     * Retrieves a specific medical record by its ID
     * @param medicalRecordID The ID of the medical record
     * @return The requested MedicalRecord object or null if not found
     */
    public MedicalRecord getMedicalRecordByID(String medicalRecordID) {
        try {
            return medicalRecordData.get(medicalRecordID);
        } catch (Exception e) {
            handleException("getting the Medical Record", e);
        }
        return null;
    }

    /**
     * Updates an existing medical record
     * @param medicalRecordID The ID of the medical record to update
     * @param pastDiagnosis The updated past diagnosis information
     * @param treatments The updated treatments information
     * @param newPrescribeMedications The updated list of prescribed medications
     * @return boolean indicating if the update was successful
     */
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

    /**
     * Deletes a medical record from the system
     * @param medicalRecordID The ID of the medical record to delete
     * @return boolean indicating if the deletion was successful
     */
    @Override
    public boolean deleteMedicalRecord(String medicalRecordID) {
        try {
            return medicalRecordData.remove(medicalRecordID) != null;
        } catch (Exception e) {
            handleException("deleting the Medical Record", e);
            return false;
        }
    }

    /**
     * Debug method to check medical record classes
     * TODO Delete later, for checking purposes
     */
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

    /**
     * Adds a medical record with a specific ID
     * @param medicalRecordID The ID for the medical record
     * @param medicalRecord The medical record to add
     */
    @Override
    public void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord) {
        try {
            medicalRecordData.put(medicalRecordID, medicalRecord);
        } catch (Exception e) {
            handleException("adding the Medical Record", e);
        }
    }

    /**
     * Stores all medical records into CSV format
     */
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
