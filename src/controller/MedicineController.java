package src.controller;

import src.model.PrescribeMedications;
import src.repository.IMedicineRepository;
import src.repository.MedicineRepository;
import src.utils.MedicationLoader;

import java.util.Scanner;


public class MedicineController {
    private static IMedicineRepository medicineRepo;

    public MedicineController (MedicineRepository medicineRepo) {
        this.medicineRepo = medicineRepo;
        loadMedications();
    }

    private void loadMedications() {
        String medicineFilePath = "./data/Medicine_List.csv";
        MedicationLoader medicationLoader = new MedicationLoader(medicineFilePath);
        medicationLoader.loadMedication();
    }

    // Modified addMedicine method to handle stock and low stock alert
    public static void addMedicine(PrescribeMedications medication, int stock, int lowStockAlert) {
        medicineRepo.addMedicine(medication, stock, lowStockAlert);
    }

    public void displayAllMedicines() {
        if (medicineRepo.getAllMedicines().isEmpty()) {
            System.out.println("No medicines available in the inventory.");
        } else {
            medicineRepo.getAllMedicines().forEach((name, medicine) -> {
                int stock = medicineRepo.getStock(name);
                int lowStockAlert = medicineRepo.getStockAlert(name);
                System.out.println("Medicine Name: " + name +
                                   ", Stock: " + stock +
                                   ", Low Stock Alert: " + lowStockAlert +
                                   ", Status: " + medicine.getStatus());
            });
        }
        medicineRepo.checkLowStock();
    }

    public void reqMedicine() {
        System.out.println("Enter the medicine name you would like to replenish:");
        Scanner sc = new Scanner(System.in);
        String medicineName = sc.nextLine();
        PrescribeMedications medication = medicineRepo.getMedicine(medicineName);
        if (medication == null) {
            System.out.println(medicineName + " is not in the inventory.");
        } else {
            int stock = medicineRepo.getStock(medicineName);
            int lowStockAlert = medicineRepo.getStockAlert(medicineName);
            if (stock < lowStockAlert) {
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
                int newStock = medicineRepo.getStock(medicineName) + quantity;
                medicineRepo.setStock(medicineName, newStock);
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

    public void addNewMedicine(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Input medicine name to add: ");
        String medicineName = sc.nextLine();
        PrescribeMedications medicine = medicineRepo.getMedicine(medicineName);
        if (medicine == null) {
            System.out.print("Input stock: ");
            int stock = sc.nextInt();
            System.out.print("Input low stock alert: ");
            int lowstock = sc.nextInt();
            medicineRepo.addMedicine(new PrescribeMedications(medicineName), stock, lowstock);
            System.out.println(medicineName + " has been added to inventory.");
        } else {
            System.out.println(medicineName + " is already in inventory!");
        }
    }

    public void removeMedicine(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Input medicine name to remove: ");
        String meddelete = sc.nextLine();
        if (medicineRepo.getMedicine(meddelete) != null) {
            medicineRepo.deleteMedicine(meddelete);
        } else {
            System.out.println(meddelete + " is not in the inventory!");
        }
    }

    public void updateMedicine(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Input medicine name to update: ");
        String medicineStock = sc.nextLine();
        PrescribeMedications checkMedicine = medicineRepo.getMedicine(medicineStock);
        if (checkMedicine != null) {
            System.out.print("Input new stock: ");
            int stockLevel = sc.nextInt();
            System.out.print("Input new low stock alert: ");
            int stockAlert = sc.nextInt();
            medicineRepo.updateMedicine(medicineStock, stockLevel, stockAlert);
            System.out.println(medicineStock + " has been updated.");
        } else {
            System.out.println(medicineStock + " is not in the inventory!");
        }
    }

}
