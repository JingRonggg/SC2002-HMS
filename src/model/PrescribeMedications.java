package src.model;

import src.enums.PrescribeMedicationsStatus;

/**
 * Represents a prescription for medications in the system.
 * This class handles the details of a medication prescription including the medicine name,
 * quantity prescribed, and the current status of the prescription.
 */
public class PrescribeMedications {
    private String medicineName;
    private int quantity;
    private PrescribeMedicationsStatus status;

    /**
     * Constructs a new PrescribeMedications instance.
     *
     * @param medicineName The name of the prescribed medicine
     * @param quantity The quantity of medicine prescribed
     * @param status The current status of the prescription
     */
    public PrescribeMedications(String medicineName, int quantity, PrescribeMedicationsStatus status) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Gets the name of the prescribed medicine.
     *
     * @return The medicine name
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Sets the name of the prescribed medicine.
     *
     * @param medicineName The new medicine name to set
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Gets the current status of the prescription.
     *
     * @return The current prescription status
     */
    public PrescribeMedicationsStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the prescription.
     *
     * @param status The new status to set
     */
    public void setStatus(PrescribeMedicationsStatus status) {
        this.status = status;
    }

    /**
     * Gets the quantity of medicine prescribed.
     *
     * @return The prescribed quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of medicine prescribed.
     *
     * @param quantity The new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
