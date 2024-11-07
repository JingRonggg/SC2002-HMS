package src.repository;

import java.util.HashMap;

import src.interfaces.IMedicineRepository;
import src.model.MedicationStorage;
import src.enums.MedicationStorageStatus;

public class MedicineRepository implements IMedicineRepository {
    protected static HashMap<String, MedicationStorage> medicationInventory = new HashMap<>();
    protected static HashMap<String, Integer> stockInventory = new HashMap<>();
    protected static HashMap<String, Integer> stockAlertInventory = new HashMap<>();

    public MedicineRepository() {}

    public void addMedicine(MedicationStorage medicationStorage, int stock, int lowStockAlert) {
        String medicineName = medicationStorage.getMedicineName();
        medicationInventory.put(medicineName, medicationStorage);
        stockInventory.put(medicineName, stock);
        stockAlertInventory.put(medicineName, lowStockAlert);
    }

    public MedicationStorage getMedicine(String medicineName) {
        return medicationInventory.get(medicineName);
    }

    public HashMap<String, MedicationStorage> getAllMedicines() {
        return medicationInventory;
    }

    public int getStock(String medicineName) {
        return stockInventory.getOrDefault(medicineName, 0);
    }

    public void setStock(String medicineName, int stock) {
        if (medicationInventory.containsKey(medicineName)) {
            stockInventory.put(medicineName, stock);
            //System.out.println(medicineName + "'s stock has been updated to: " + stock);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    public int getStockAlert(String medicineName) {
        return stockAlertInventory.getOrDefault(medicineName, 0);
    }

    public void setStockAlert(String medicineName, int lowStockAlert) {
        if (medicationInventory.containsKey(medicineName)) {
            stockAlertInventory.put(medicineName, lowStockAlert);
            //System.out.println(medicineName + "'s low stock alert has been updated to: " + lowStockAlert);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.\n");
        }
    }

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

    public void updateStatus(String medicineName, String status){
        MedicationStorage medication = medicationInventory.get(medicineName);
        if (medication != null) {
            medication.setStatus(MedicationStorageStatus.valueOf(status));
            //System.out.println("\nUpdated status of " + medicineName + " to " + status);
        } else {
            System.out.println(medicineName + " not found in inventory.\n");
        }
    }

    public void checkReplenishReq() {
        for (MedicationStorage medication : medicationInventory.values()) {
            if (MedicationStorageStatus.REQUESTED == medication.getStatus()) {
                System.out.println("\n" + medication.getMedicineName() + " needs to be replenished.");
            }
        }
    }

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

