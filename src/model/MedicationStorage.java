package src.model;

/**
 * Represents storage information for a medication in the system.
 * Tracks the medication name and its current availability status.
 */
public class MedicationStorage {
    private String medicineName;
    private MedicationStorageStatus status;

    /**
     * Creates a new MedicationStorage instance.
     * @param medicineName The name of the medication to store
     */
    public MedicationStorage(String medicineName) {
        this.medicineName = medicineName;
        this.status = MedicationStorageStatus.AVAILABLE;
    }

    /**
     * Gets the name of the stored medication.
     * @return The medication name
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Sets the name of the stored medication.
     * @param medicineName The new medication name
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Gets the current storage status of the medication.
     * @return The medication storage status
     */
    public MedicationStorageStatus getStatus() {
        return status;
    }

    /**
     * Updates the storage status of the medication.
     * @param status The new storage status
     */
    public void setStatus(MedicationStorageStatus status) {
        this.status = status;
    }
}

