package src.interfaces;

import java.util.HashMap;

import src.model.MedicationStorage;

/**
 * Interface defining operations for managing medicine inventory
 */
public interface IMedicineRepository {
    
    /**
     * Adds a new medicine to the inventory
     * @param medicationStorage The medication storage object containing medicine details
     * @param stock Initial stock quantity
     * @param lowStockAlert Threshold for low stock alert
     */
    public void addMedicine(MedicationStorage medicationStorage, int stock, int lowStockAlert);

    /**
     * Retrieves medicine details by name
     * @param medicineName Name of the medicine
     * @return MedicationStorage object containing medicine details
     */
    public MedicationStorage getMedicine(String medicineName);
    
    /**
     * Gets all medicines in the inventory
     * @return HashMap containing medicine names mapped to their storage details
     */
    public HashMap<String, MedicationStorage> getAllMedicines();

    /**
     * Gets current stock level for a medicine
     * @param medicineName Name of the medicine
     * @return Current stock quantity
     */
    public int getStock(String medicineName);

    /**
     * Updates stock level for a medicine
     * @param medicineName Name of the medicine
     * @param stock New stock quantity
     */
    public void setStock(String medicineName, int stock);

    /**
     * Gets low stock alert threshold for a medicine
     * @param medicineName Name of the medicine
     * @return Low stock alert threshold
     */
    public int getStockAlert(String medicineName);

    /**
     * Sets low stock alert threshold for a medicine
     * @param medicineName Name of the medicine
     * @param lowStockAlert New low stock alert threshold
     */
    public void setStockAlert(String medicineName, int lowStockAlert);

    /**
     * Checks inventory for medicines below their low stock threshold
     */
    public void checkLowStock();

    /**
     * Updates the status of a medicine
     * @param medicineName Name of the medicine
     * @param status New status to set
     */
    public void updateStatus(String medicineName, String status);

    /**
     * Checks for medicines requiring replenishment
     */
    public void checkReplenishReq();

    /**
     * Removes a medicine from the inventory
     * @param medicineName Name of the medicine to delete
     */
    public void deleteMedicine(String medicineName);

    /**
     * Updates medicine details in the inventory
     * @param medicineName Name of the medicine
     * @param stock New stock quantity
     * @param lowStockAlert New low stock alert threshold
     */
    public void updateMedicine(String medicineName, int stock, int lowStockAlert);

}
