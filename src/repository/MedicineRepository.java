package src.repository;

import src.model.MedicationStorageStatus;
import src.interfaces.IMedicineRepository;
import src.model.MedicationStorage;

import java.util.HashMap;

/**
 * Repository class for managing medication inventory and stock levels.
 * Implements the IMedicineRepository interface.
 */
public class MedicineRepository implements IMedicineRepository {
    /** Map storing medication details keyed by medicine name */
    protected static HashMap<String, MedicationStorage> medicationInventory = new HashMap<>();
    /** Map storing current stock levels keyed by medicine name */
    protected static HashMap<String, Integer> stockInventory = new HashMap<>();
    /** Map storing low stock alert thresholds keyed by medicine name */
    protected static HashMap<String, Integer> stockAlertInventory = new HashMap<>();

    /**
     * Default constructor
     */
    public MedicineRepository() {}

    /**
     * Adds a new medicine to the inventory with initial stock and alert levels
     * @param medicationStorage The medication storage object containing medicine details
     * @param stock Initial stock quantity
     * @param lowStockAlert Threshold for low stock alerts
     */
    public void addMedicine(MedicationStorage medicationStorage, int stock, int lowStockAlert) {
        String medicineName = medicationStorage.getMedicineName();
        medicationInventory.put(medicineName, medicationStorage);
        stockInventory.put(medicineName, stock);
        stockAlertInventory.put(medicineName, lowStockAlert);
    }

    /**
     * Retrieves medication details by name
     * @param medicineName Name of the medicine to retrieve
     * @return MedicationStorage object if found, null otherwise
     */
    public MedicationStorage getMedicine(String medicineName) {
        return medicationInventory.get(medicineName);
    }

    /**
     * Gets all medicines in the inventory
     * @return HashMap containing all medication storage objects
     */
    public HashMap<String, MedicationStorage> getAllMedicines() {
        return medicationInventory;
    }

    /**
     * Gets current stock level for a medicine
     * @param medicineName Name of the medicine
     * @return Current stock quantity, 0 if medicine not found
     */
    public int getStock(String medicineName) {
        return stockInventory.getOrDefault(medicineName, 0);
    }

    /**
     * Updates stock level for a medicine
     * @param medicineName Name of the medicine
     * @param stock New stock quantity
     */
    public void setStock(String medicineName, int stock) {
        if (medicationInventory.containsKey(medicineName)) {
            stockInventory.put(medicineName, stock);
            //System.out.println(medicineName + "'s stock has been updated to: " + stock);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    /**
     * Gets low stock alert threshold for a medicine
     * @param medicineName Name of the medicine
     * @return Alert threshold quantity, 0 if medicine not found
     */
    public int getStockAlert(String medicineName) {
        return stockAlertInventory.getOrDefault(medicineName, 0);
    }

    /**
     * Updates low stock alert threshold for a medicine
     * @param medicineName Name of the medicine
     * @param lowStockAlert New alert threshold quantity
     */
    public void setStockAlert(String medicineName, int lowStockAlert) {
        if (medicationInventory.containsKey(medicineName)) {
            stockAlertInventory.put(medicineName, lowStockAlert);
            //System.out.println(medicineName + "'s low stock alert has been updated to: " + lowStockAlert);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.\n");
        }
    }

    /**
     * Checks inventory for medicines below their low stock threshold
     * Prints alerts for medicines with stock below their alert threshold
     */
    public void checkLowStock() {
        boolean alertGenerated = false;  
        for (String medicineName : medicationInventory.keySet()) {
            if (getStock(medicineName) < getStockAlert(medicineName)) {
                if (!alertGenerated){
                    System.out.println("\n==================== ALERTS ========================");
                    alertGenerated = true;
                }
                System.out.println("ALERT: " + medicineName + " IS BELOW THE LOW STOCK THRESHOLD!");
            }
        }
        
        if (alertGenerated){System.out.println("====================================================\n");}
    }

    /**
     * Updates the status of a medicine
     * @param medicineName Name of the medicine
     * @param status New status to set
     */
    public void updateStatus(String medicineName, String status){
        MedicationStorage medication = medicationInventory.get(medicineName);
        if (medication != null) {
            medication.setStatus(MedicationStorageStatus.valueOf(status));
            //System.out.println("\nUpdated status of " + medicineName + " to " + status);
        } else {
            System.out.println(medicineName + " not found in inventory.\n");
        }
    }

    /**
     * Checks and prints medicines that need replenishment
     * Identifies medicines with REQUESTED status
     */
    public void checkReplenishReq() {
        for (MedicationStorage medication : medicationInventory.values()) {
            if (MedicationStorageStatus.REQUESTED == medication.getStatus()) {
                System.out.println("\n" + medication.getMedicineName() + " needs to be replenished.");
            }
        }
    }

    /**
     * Removes a medicine from the inventory
     * @param medicineName Name of the medicine to delete
     */
    public void deleteMedicine(String medicineName){
        if (medicationInventory.containsKey(medicineName)) {
            // Remove medicine from all related inventories
            medicationInventory.remove(medicineName);
            stockInventory.remove(medicineName);
            stockAlertInventory.remove(medicineName);
            System.out.println("\n" + medicineName + " has been removed from the inventory.");
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.\n");
        }
    }

    /**
     * Updates stock and alert levels for an existing medicine
     * @param medicineName Name of the medicine to update
     * @param stock New stock quantity
     * @param lowStockAlert New low stock alert threshold
     */
    public void updateMedicine(String medicineName, int stock, int lowStockAlert) {
        
        if (medicationInventory.containsKey(medicineName)) {
            setStock(medicineName, stock);
            setStockAlert(medicineName, lowStockAlert);
            System.out.println("\n" + medicineName + " has been updated with new stock and stock alert.");
        } else {
            System.out.println("\nMedicine " + medicineName + " not found in inventory.");
        }
    }

}
