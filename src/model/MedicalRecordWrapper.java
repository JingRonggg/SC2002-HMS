package src.model;

/**
 * A wrapper class that contains a medical record and its ID.
 */
public class MedicalRecordWrapper {
    /** The unique identifier for the medical record */
    private String MedicalRecordID;
    /** The medical record object */
    private MedicalRecord MedicalRecord;

    /**
     * Constructs a new MedicalRecordWrapper with the specified ID and medical record.
     * @param MedicalRecordID The unique identifier for the medical record
     * @param MedicalRecord The medical record object
     */
    public MedicalRecordWrapper(String MedicalRecordID, MedicalRecord MedicalRecord) {
        this.MedicalRecordID= MedicalRecordID;
        this.MedicalRecord = MedicalRecord;
    }

    /**
     * Gets the medical record ID.
     * @return The medical record ID
     */
    public String getMedicalRecordID() {
        return MedicalRecordID;
    }

    /**
     * Sets the medical record ID.
     * @param MedicalRecordID The new medical record ID
     */
    public void setMedicalRecordID(String MedicalRecordID) {
        this.MedicalRecordID = MedicalRecordID;
    }

    /**
     * Gets the medical record.
     * @return The medical record object
     */
    public MedicalRecord getMedicalRecord() {
        return MedicalRecord;
    }

    /**
     * Sets the medical record.
     * @param MedicalRecord The new medical record object
     */
    public void setMedicalRecord(MedicalRecord MedicalRecord) {
        this.MedicalRecord = MedicalRecord;
    }
}
