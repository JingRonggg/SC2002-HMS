package src.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a medical record containing patient, doctor, diagnosis, treatment and medication information.
 */
public class MedicalRecord {
    private String medicalRecordID;
    private String doctorID;
    private String patientID;
    private PastDiagnosis pastDiagnosis;
    private Treatments treatments;
    private List<PrescribeMedications> prescribeMedications;

    /**
     * Constructs a new MedicalRecord with the specified details.
     *
     * @param medicalRecordID Unique identifier for the medical record
     * @param doctorID Unique identifier for the doctor
     * @param patientID Unique identifier for the patient
     * @param pastDiagnosis Past diagnosis information
     * @param treatments Treatment information
     * @param prescribeMedications List of prescribed medications
     */
    public MedicalRecord(String medicalRecordID, String doctorID, String patientID,
                         PastDiagnosis pastDiagnosis, Treatments treatments,
                         List<PrescribeMedications> prescribeMedications) {
        this.medicalRecordID = medicalRecordID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.pastDiagnosis = pastDiagnosis;
        this.treatments = treatments;
        this.prescribeMedications = prescribeMedications != null ? prescribeMedications : new ArrayList<>();
    }

    /**
     * Sets the medical record ID.
     * @param medicalRecordID New medical record ID
     */
    public void setMedicalRecordID(String medicalRecordID) {
        this.medicalRecordID = medicalRecordID;
    }

    /**
     * Sets the patient ID.
     * @param patientID New patient ID
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Sets the doctor ID.
     * @param doctorID New doctor ID
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Sets the past diagnosis information.
     * @param pastDiagnosis New past diagnosis information
     */
    public void setPastDiagnosis(PastDiagnosis pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    /**
     * Sets the treatments information.
     * @param treatments New treatments information
     */
    public void setTreatments(Treatments treatments) {
        this.treatments = treatments;
    }

    /**
     * Sets the list of prescribed medications.
     * @param prescribeMedications New list of prescribed medications
     */
    public void setPrescribeMedications(List<PrescribeMedications> prescribeMedications) {
        this.prescribeMedications = prescribeMedications != null ? prescribeMedications : new ArrayList<>();
    }

    /**
     * Gets the list of prescribed medications.
     * @return List of prescribed medications
     */
    public List<PrescribeMedications> getPrescribeMedications() {
        return prescribeMedications;
    }

    /**
     * Gets the medical record ID.
     * @return Medical record ID
     */
    public String getMedicalRecordID() {
        return medicalRecordID;
    }

    /**
     * Gets the patient ID.
     * @return Patient ID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the doctor ID.
     * @return Doctor ID
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the past diagnosis information.
     * @return Past diagnosis information
     */
    public PastDiagnosis getPastDiagnosis() {
        return pastDiagnosis;
    }

    /**
     * Gets the treatments information.
     * @return Treatments information
     */
    public Treatments getTreatments() {
        return treatments;
    }
}
