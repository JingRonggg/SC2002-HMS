package src.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import src.controller.MedicineController;
import src.model.PrescribeMedications;
import src.repository.MedicineRepository;
import src.controller.MedicineController;

public class MedicationLoader {
    String filepath;
    private final MedicineController medicineController; // Use MedicineController to manage inventory

    public MedicationLoader(String filepath) {
        this.filepath = filepath;

        MedicineRepository medicineRepository = new MedicineRepository();
        this.medicineController = new MedicineController(medicineRepository);
    }

    // Method to load the CSV file into the medication inventory
    public void loadMedication() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");  // Assuming CSV is comma-separated
                String medicinename = data[0].trim();
                int stock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                // Create a Medication object and store it in the inventory
                PrescribeMedications medication = new PrescribeMedications(medicinename, stock, lowStockAlert);
                medicineController.addMedicine(medication); // Use MedicineController to add medicine
            }

            medicineController.displayAllMedicines();


        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

}