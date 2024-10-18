package src.model;

public class PrescribeMedications {
    private String medicineName;
    private String status;

    public PrescribeMedications(String medicineName) {
        this.medicineName = medicineName;
        this.status = "disperse";
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void newMedicine(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

