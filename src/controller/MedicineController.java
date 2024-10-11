package src.controller;

import java.util.HashMap;
import src.model.PrescribeMedications;

public class MedicineController {
    private HashMap<String, PrescribeMedications> medicationInventory;

    public MedicineController() {
        medicationInventory = new HashMap<>();
    }

    // Method to add a medicine to the inventory
    public void addMedicine(PrescribeMedications medication) {
        String medicineName = medication.getMedicineName();
        medicationInventory.put(medicineName, medication);
        System.out.println(medicineName + " has been added to the inventory.");
    }

    // Method to get a medicine by its name
    public PrescribeMedications getMedicine(String medicineName) {
        return medicationInventory.get(medicineName);
    }

    // Method to update the stock of a medicine
    public void updateStock(String medicineName, int newStock) {
        PrescribeMedications medication = medicationInventory.get(medicineName);
        if (medication != null) {
            medication.setStock(newStock);
            System.out.println(medicineName + "'s stock has been updated to: " + newStock);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    // Method to check low stock levels and alert
    public void checkLowStock() {
        for (PrescribeMedications medication : medicationInventory.values()) {
            if (medication.getStock() < medication.getStockAlert()) {
                System.out.println("Alert: " + medication.getMedicineName() + " is below the low stock threshold.");
            }
        }
    }

    // Method to get all medicines in the inventory
    public HashMap<String, PrescribeMedications> getAllMedicines() {
        return medicationInventory;
    }
}
