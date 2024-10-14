package src.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientID;
    private List<PastDiagnosis> pastDiagnosis;
    private List<Treatments> treatments;
    private List<PrescribeMedications> prescribeMedications;

    public MedicalRecord(String patientID){
        this.patientID = patientID;
        this.pastDiagnosis = new ArrayList<PastDiagnosis>();
        this.treatments = new ArrayList<Treatments>();
        this.prescribeMedications = new ArrayList<PrescribeMedications>();
    }

    public String getPatientID() {
        return patientID;
    }

    public List<PastDiagnosis> getPastDiagnosis() {
        return pastDiagnosis;
    }

    public List<Treatments> getTreatments(){
        return treatments;
    }

    public List<PrescribeMedications> getPrescribeMedications(){
        return prescribeMedications;
    }

    public void addPrescribeMedications(PrescribeMedications prescribeMedications){
        this.prescribeMedications.add(prescribeMedications);
    }

    public void addPastDiagnosis(PastDiagnosis pastDiagnosis){
        this.pastDiagnosis.add(pastDiagnosis);
    }

    public void addTreatments(Treatments treatments){
        this.treatments.add(treatments);
    }
}
