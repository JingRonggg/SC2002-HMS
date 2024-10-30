package src.model;

import src.enums.PrescribeMedicationsStatus;

public class PrescribeMedications {
    private String medicineName;
    private int quantity;
    private PrescribeMedicationsStatus status;

    public PrescribeMedications(String medicineName, int quantity, PrescribeMedicationsStatus status) {
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

    public PrescribeMedicationsStatus getStatus() {
        return status;
    }

    public void setStatus(PrescribeMedicationsStatus status) {
        this.status = status;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

