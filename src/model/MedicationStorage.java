package src.model;

public class MedicationStorage {
    private String medicineName;
    private MedicationStorageStatus status;

    public MedicationStorage(String medicineName) {
        this.medicineName = medicineName;
        this.status = MedicationStorageStatus.AVAILABLE;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public MedicationStorageStatus getStatus() {
        return status;
    }

    public void setStatus(MedicationStorageStatus status) {
        this.status = status;
    }
}


