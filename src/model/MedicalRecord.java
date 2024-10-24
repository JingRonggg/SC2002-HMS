package src.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String medicalRecordID;
    private String doctorID;
    private String patientID;
    private PastDiagnosis pastDiagnosis;
    private Treatments treatments;
    private List<PrescribeMedications> prescribeMedications;

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

    public void setMedicalRecordID(String medicalRecordID) {
        this.medicalRecordID = medicalRecordID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public void setPastDiagnosis(PastDiagnosis pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    public void setTreatments(Treatments treatments) {
        this.treatments = treatments;
    }

    public void setPrescribeMedications(List<PrescribeMedications> prescribeMedications) {
        this.prescribeMedications = prescribeMedications != null ? prescribeMedications : new ArrayList<>();
    }

    public List<PrescribeMedications> getPrescribeMedications() {
        return prescribeMedications;
    }

    public String getMedicalRecordID() {
        return medicalRecordID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public PastDiagnosis getPastDiagnosis() {
        return pastDiagnosis;
    }

    public Treatments getTreatments() {
        return treatments;
    }
}
