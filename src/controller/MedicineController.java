package src.controller;

import java.util.HashMap;
import src.model.PrescribeMedications;
import src.repository.IMedicineRepository;
import src.repository.MedicineRepository;
import java.util.Scanner;


public class MedicineController {
    private final IMedicineRepository medicineRepo;

    public MedicineController (IMedicineRepository medicineRepo){
        this.medicineRepo = medicineRepo;
    }

    public void addMedicine(PrescribeMedications medication) {
        medicineRepo.addMedicine(medication);
    }

    public void displayAllMedicines() {
        
        if (medicineRepo.getAllMedicines().isEmpty()) {
            System.out.println("No medicines available in the inventory.");
        } else {
            medicineRepo.getAllMedicines().forEach((name, medicine) -> {
                System.out.println("Medicine Name: " + name + 
                                   ", Stock: " + medicine.getStock() + 
                                   ", Low Stock Alert: " + medicine.getStockAlert() + 
                                   ", Status: " + medicine.getStatus());
            });
        }
        medicineRepo.checkLowStock();
    }

    public void reqMedicine(String medicineName) {
        PrescribeMedications medication = medicineRepo.getMedicine(medicineName);
        if (medication == null) {
            System.out.println(medicineName + " is not in the inventory.");
        } else {
            if (medication.getStock() < medication.getStockAlert()) {
                medicineRepo.updateStatus(medicineName, "replenish");
                System.out.println("Requesting replenishment for " + medicineName);
            } else {
                System.out.println(medicineName + " has sufficient stock.");
            }
        }
    }
    
    public void replenishMedicine() {
        // Check for any replenishment requests 
        medicineRepo.checkReplenishReq();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the medication name to replenish: ");
        String medicineName = sc.nextLine();
        PrescribeMedications medication = medicineRepo.getMedicine(medicineName);
        if (medication != null) {
            if ("replenish".equals(medication.getStatus())) {
                System.out.print("Enter the quantity to add: ");
                int quantity = sc.nextInt();
                int newStock = medication.getStock() + quantity;
                medicineRepo.updateStock(medicineName, newStock);
                medicineRepo.updateStatus(medicineName, "disperse"); // Reset status to 'disperse'
                System.out.println("Accepted replenishment for " + medicineName + 
                                   ". New stock: " + newStock + " units. Status: " + medication.getStatus());
            } else {
                System.out.println("Medicine " + medicineName + " is not eligible for replenishment (status: " + medication.getStatus() + ").");
            }
        } else {
            System.out.println("Medicine " + medicineName + " is not in inventory, cannot accept replenishment.");
        }
    }
}
