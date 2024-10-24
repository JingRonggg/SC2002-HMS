package src.controller;

import src.appointment.Appointment;
import src.appointment.IAppointmentRepository;
import src.model.MedicalRecord;
import src.model.MedicationStorage;
import src.model.PrescribeMedications;
import src.repository.IMedicalRecordRepository;
import src.repository.IMedicineRepository;
import src.repository.MedicineRepository;
import src.utils.MedicationLoader;

import java.util.*;


public class MedicineController {
    private static IMedicineRepository medicineRepo;
    private static IMedicalRecordRepository medicalRecordRepo;
    private static IAppointmentRepository appointmentRepo;

    public MedicineController (IMedicineRepository medicineRepo, IMedicalRecordRepository medicalRecordRepo, IAppointmentRepository appointmentRepo) {
        this.medicineRepo = medicineRepo;
        loadMedications();
        this.medicalRecordRepo = medicalRecordRepo;
        this.appointmentRepo = appointmentRepo;
    }

    private void loadMedications() {
        String medicineFilePath = "./data/Medicine_List.csv";
        MedicationLoader medicationLoader = new MedicationLoader(medicineFilePath);
        medicationLoader.loadMedication();
    }

    // Modified addMedicine method to handle stock and low stock alert
    public static void addMedicine(MedicationStorage medicationStorage, int stock, int lowStockAlert) {
        medicineRepo.addMedicine(medicationStorage, stock, lowStockAlert);
    }

    public void displayAllMedicines() {
        System.out.println("Medication Inventory:");
        System.out.println("===============================================================");
        System.out.printf("%-20s %-10s %-20s %-10s%n", "Medicine Name", "Stock", "Low Stock Alert", "Status");
        System.out.println("===============================================================");
        
        if (medicineRepo.getAllMedicines().isEmpty()) {
            System.out.println("No medicines available in the inventory.");
        } else {
            medicineRepo.getAllMedicines().forEach((name, medicine) -> {
                int stock = medicineRepo.getStock(name);
                int lowStockAlert = medicineRepo.getStockAlert(name);
                String status = medicine.getStatus();
                
                System.out.printf("%-20s %-10d %-20d %-10s%n", name, stock, lowStockAlert, status);
            });
        }
        System.out.println("===============================================================");
        medicineRepo.checkLowStock();
    }

    public void reqMedicine() {
        System.out.print("Enter the medicine name you would like to replenish: ");
        Scanner sc = new Scanner(System.in);
        String medicineName = sc.nextLine();
        MedicationStorage medication = medicineRepo.getMedicine(medicineName);
        if (medication == null) {
            System.out.println(medicineName + " is not in the inventory.");
        } else {
            int stock = medicineRepo.getStock(medicineName);
            int lowStockAlert = medicineRepo.getStockAlert(medicineName);
            if (stock < lowStockAlert) {
                medicineRepo.updateStatus(medicineName, "requested");
                System.out.println("Requesting replenishment for " + medicineName + "!\n");
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
        MedicationStorage medication = medicineRepo.getMedicine(medicineName);
        if (medication != null) {
            if ("replenish".equals(medication.getStatus())) {
                System.out.print("Enter the quantity to add: ");
                int quantity = sc.nextInt();
                int newStock = medicineRepo.getStock(medicineName) + quantity;
                medicineRepo.setStock(medicineName, newStock);
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
        String medicineName = sc.nextLine().trim();  
        if (medicineName.isEmpty()) {
            System.out.println("Medicine name cannot be empty. Please try again.");
        } else {
            MedicationStorage medicine = medicineRepo.getMedicine(medicineName);
            
            if (medicine == null) {
                try {
                    System.out.print("Input stock: ");
                    int stock = sc.nextInt();
                    System.out.print("Input low stock alert: ");
                    int lowstock = sc.nextInt();
                    medicineRepo.addMedicine(new MedicationStorage(medicineName), stock, lowstock);
                    System.out.println("\n" + medicineName + " has been added to inventory!");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer for stock and stock alert.\n");
                    sc.nextLine();  // Clear the invalid input from the scanner
                }
            } else {
                System.out.println(medicineName + " is already in inventory!\n");
            }
}

    }

    public void removeMedicine(){
        if (medicineRepo.getAllMedicines().isEmpty()) {
            System.out.println("Inventory is empty. Cannot remove any medicine.\n");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Input medicine name to remove: ");
        String meddelete = sc.nextLine();
        if (medicineRepo.getMedicine(meddelete) != null) {
            medicineRepo.deleteMedicine(meddelete);
        } else {
            System.out.println(meddelete + " is not in the inventory!\n");
        }
    }

    public void updateMedicine(){
        if (medicineRepo.getAllMedicines().isEmpty()) {
            System.out.println("Inventory is empty. Cannot update status.\n");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Input medicine name to update: ");
        String medicineStock = sc.nextLine();
        MedicationStorage checkMedicine = medicineRepo.getMedicine(medicineStock);
        if (checkMedicine != null) {
            try {
                System.out.print("Input new stock: ");
                int stockLevel = sc.nextInt();  // Potential InputMismatchException if input is not an integer
                
                System.out.print("Input new low stock alert: ");
                int stockAlert = sc.nextInt();  // Potential InputMismatchException if input is not an integer
                
                medicineRepo.updateMedicine(medicineStock, stockLevel, stockAlert);
                //System.out.println(medicineStock + " has a stock of " + stockLevel + " and stock alert of " + stockAlert);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for stock and stock alert.\n");
                sc.nextLine();  // Clear the invalid input from the scanner
            }
        } else {
            System.out.println(medicineStock + " is not in the inventory!\n");
        }
    }

    public void dispenseMedicine(String medicalRecordID) {
        MedicalRecord medicalRecord = medicalRecordRepo.getMedicalRecordByID(medicalRecordID);
        StringBuilder undispensedMedicines = new StringBuilder();

        if (medicalRecord != null) {
            for (PrescribeMedications prescribedMed : medicalRecord.getPrescribeMedications()) {
                String medicineName = prescribedMed.getMedicineName();
                int requiredQuantity = prescribedMed.getQuantity();

                MedicationStorage medication = medicineRepo.getMedicine(medicineName);
                if (medication == null) {
                    undispensedMedicines.append(medicineName).append(" (not available in inventory); ");
                    prescribedMed.setStatus("not dispensed");
                    continue;
                }

                int availableStock = medicineRepo.getStock(medicineName);
                if (availableStock < requiredQuantity) {
                    if (availableStock > 0) {
                        prescribedMed.setStatus("not dispensed");
                        System.out.println("Dispensed " + availableStock + " of " + medicineName + ".");
                    }
                    undispensedMedicines.append(medicineName).append(" (not enough stock, please come back later); ");
                } else {
                    // Fully dispense the required quantity
                    medicineRepo.setStock(medicineName, availableStock - requiredQuantity);
                    prescribedMed.setStatus("Dispensed");
                    System.out.println("Dispensed " + requiredQuantity + " of " + medicineName + ".");
                }
            }

            // Update the medical record after processing all medications
            medicalRecordRepo.updateMedicalRecord(medicalRecordID, null, null, medicalRecord.getPrescribeMedications());

            // If there are any undispensed medications, inform the patient
            if (!undispensedMedicines.isEmpty()) {
                System.out.println("Please come back for the following medications: " + undispensedMedicines.toString());
            }
        } else {
            System.out.println("Medical record not found.");
        }
    }

//    public Map<String, Appointment> getAllCompletedAppointments() {
////        return appointmentRepo.getAllCompletedAppointment();
//    }
}
