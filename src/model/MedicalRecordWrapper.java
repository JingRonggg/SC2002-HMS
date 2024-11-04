package src.model;

public class MedicalRecordWrapper {
    private String MedicalRecordID;
    private MedicalRecord MedicalRecord;

    public MedicalRecordWrapper(String MedicalRecordID, MedicalRecord MedicalRecord) {
        this.MedicalRecordID= MedicalRecordID;
        this.MedicalRecord = MedicalRecord;
    }
    public String getMedicalRecordID() {
        return MedicalRecordID;
    }
    public void setMedicalRecordID(String MedicalRecordID) {
        this.MedicalRecordID = MedicalRecordID;
    }
    public MedicalRecord getMedicalRecord() {
        return MedicalRecord;
    }
    public void setMedicalRecord(MedicalRecord MedicalRecord) {
        this.MedicalRecord = MedicalRecord;
    }
}
