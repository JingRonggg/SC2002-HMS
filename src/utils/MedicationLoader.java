package src.utils;

import src.controller.MedicineController;
import src.model.PrescribeMedications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MedicationLoader {
    private String filePath;

    public MedicationLoader(String filePath) {
        this.filePath = filePath;
    }

    // Method to load the CSV file into the medication inventory
    public void loadMedication() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");  // Assuming CSV is comma-separated
                String medicineName = data[0].trim();
                int stock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                // Create a Medication object and store it in the inventory
                PrescribeMedications medication = new PrescribeMedications(medicineName, stock, lowStockAlert);
                MedicineController.addMedicine(medication); // Use MedicineController to add medicine
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

}