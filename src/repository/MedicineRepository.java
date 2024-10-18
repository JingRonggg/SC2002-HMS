package src.repository;

import java.util.HashMap;
import src.model.PrescribeMedications;

public class MedicineRepository implements IMedicineRepository {
    protected static HashMap<String, PrescribeMedications> medicationInventory = new HashMap<>();
    protected static HashMap<String, Integer> stockInventory = new HashMap<>();
    protected static HashMap<String, Integer> stockAlertInventory = new HashMap<>();

    public MedicineRepository() {}

    public void addMedicine(PrescribeMedications medication, int stock, int lowStockAlert) {
        String medicineName = medication.getMedicineName();
        medicationInventory.put(medicineName, medication);
        stockInventory.put(medicineName, stock);
        stockAlertInventory.put(medicineName, lowStockAlert);
    }

    public PrescribeMedications getMedicine(String medicineName) {
        return medicationInventory.get(medicineName);
    }

    public HashMap<String, PrescribeMedications> getAllMedicines() {
        return medicationInventory;
    }

    public int getStock(String medicineName) {
        return stockInventory.getOrDefault(medicineName, 0);
    }

    public void setStock(String medicineName, int stock) {
        if (medicationInventory.containsKey(medicineName)) {
            stockInventory.put(medicineName, stock);
            System.out.println(medicineName + "'s stock has been updated to: " + stock);
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
            System.out.println(medicineName + "'s low stock alert has been updated to: " + lowStockAlert);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    public void checkLowStock() {
        for (String medicineName : medicationInventory.keySet()) {
            if (getStock(medicineName) < getStockAlert(medicineName)) {
                System.out.println("Alert: " + medicineName + " is below the low stock threshold.");
            }
        }
    }

    public void updateStatus(String medicineName, String status) {
        PrescribeMedications medication = medicationInventory.get(medicineName);
        if (medication != null) {
            medication.setStatus(status);
            System.out.println("Updated status of " + medicineName + " to " + status);
        } else {
            System.out.println(medicineName + " not found in inventory.");
        }
    }

    public void checkReplenishReq() {
        for (PrescribeMedications medication : medicationInventory.values()) {
            if ("replenish".equals(medication.getStatus())) {
                System.out.println(medication.getMedicineName() + " needs to be replenished.");
            }
        }
    }

    public void deleteMedicine(String medicineName){
        if (medicationInventory.containsKey(medicineName)) {
            // Remove medicine from all related inventories
            medicationInventory.remove(medicineName);
            stockInventory.remove(medicineName);
            stockAlertInventory.remove(medicineName);
            System.out.println(medicineName + " has been removed from the inventory.");
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

}

