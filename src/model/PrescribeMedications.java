package src.model;

public class PrescribeMedications {
    private String medicineName;
    private int quantity;
    private String status;

    public PrescribeMedications(String medicineName, int quantity, String status) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.status = status;
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


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

