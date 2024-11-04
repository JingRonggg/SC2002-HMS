package src.utils;

import src.controller.MedicineController;
import src.model.MedicationStorage;
import src.model.PrescribeMedications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MedicationLoader {
    private String filePath;

    public MedicationLoader(String filePath) {
        this.filePath = filePath;
    }

    public void loadMedication() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");  // Assuming CSV is comma-separated
                String medicineName = data[0].trim();
                int stock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                // Create a Medication object without stock details
                MedicationStorage medicationStorage = new MedicationStorage(medicineName);

                // Add medication using MedicineController, passing stock and alert info separately
                MedicineController.addMedicine(medicationStorage, stock, lowStockAlert);
                
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
