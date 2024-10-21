package src.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientID;
    private PastDiagnosis pastDiagnosis;
    private Treatments treatments;
    private PrescribeMedications prescribeMedications;

    public MedicalRecord(String patientID){
        this.patientID = patientID;
        this.pastDiagnosis = null;
        this.treatments = null;
        this.prescribeMedications = null;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setPastDiagnosis(PastDiagnosis pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    public void setTreatments(Treatments treatments) {
        this.treatments = treatments;
    }

    public void setPrescribeMedications(PrescribeMedications prescribeMedications) {
        this.prescribeMedications = prescribeMedications;
    }

    public String getPatientID() {
        return patientID;
    }

    public PastDiagnosis getPastDiagnosis() {
        return pastDiagnosis;
    }

    public Treatments getTreatments(){
        return treatments;
    }

    public PrescribeMedications getPrescribeMedications(){
        return prescribeMedications;
    }

}
