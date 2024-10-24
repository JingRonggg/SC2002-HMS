package src.model;

public class MedicationStorage {
    private String medicineName;
    private String status;

    public MedicationStorage(String medicineName) {
        this.medicineName = medicineName;
        this.status = null;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


