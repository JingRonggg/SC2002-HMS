package src.repository;
import java.util.HashMap;
import src.model.PrescribeMedications;

public class MedicineRepository implements IMedicineRepository{
    protected HashMap<String, PrescribeMedications> medicationInventory = new HashMap<>();

    public void addMedicine(PrescribeMedications medication) {
        String medicineName = medication.getMedicineName();
        medicationInventory.put(medicineName, medication);
        System.out.println(medicineName + " has been added to the inventory.");
    }

    public PrescribeMedications getMedicine(String medicineName) {
        return medicationInventory.get(medicineName.trim().toLowerCase());
    }

    
    public void updateStock(String medicineName, int newStock) {
        PrescribeMedications medication = medicationInventory.get(medicineName);
        if (medication != null) {
            medication.setStock(newStock);
            System.out.println(medicineName + "'s stock has been updated to: " + newStock);
        } else {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    public void checkLowStock() {
        for (PrescribeMedications medication : medicationInventory.values()) {
            if (medication.getStock() < medication.getStockAlert()) {
                System.out.println("Alert: " + medication.getMedicineName() + " is below the low stock threshold.");
            }
        }
    }

    public HashMap<String, PrescribeMedications> getAllMedicines() {
        return medicationInventory;
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

    public void checkReplenishReq(){
        for (PrescribeMedications medication : medicationInventory.values()) {
            if ("replenish".equals(medication.getStatus())) {
                System.out.println( medication.getMedicineName() + " needs to be replenished");
            }
        }
    }
}